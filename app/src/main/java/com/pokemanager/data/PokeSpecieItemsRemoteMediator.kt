package com.pokemanager.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.pokemanager.data.local.PokeManagerDatabase
import com.pokemanager.data.local.entities.*
import com.pokemanager.data.mappers.fromResponseListToPokeTypeEntityList
import com.pokemanager.data.mappers.toPokeSpecieEntity
import com.pokemanager.data.remote.PokeManagerApi
import com.pokemanager.data.remote.responses.PokemonItemFromListResponse
import com.pokemanager.utils.Constants.LAST_VALID_POKEMON_NUMBER
import com.pokemanager.utils.Constants.POKEMON_PAGING_STARTING_KEY
import com.pokemanager.utils.Utils
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PokeSpecieItemsRemoteMediator(
    private val pokeManagerApi: PokeManagerApi,
    private val pokeDatabase: PokeManagerDatabase
) : RemoteMediator<Int, PokeSpecieWithTypes>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, PokeSpecieWithTypes>): MediatorResult {
        val offset = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: POKEMON_PAGING_STARTING_KEY
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val pokeSpeciesResponse = pokeManagerApi.getPokemonItemsNetwork(state.config.pageSize, offset).results
            val endOfPaginationReached = pokeSpeciesResponse.isEmpty()

            val pokeSpecieEntities = mutableListOf<PokeSpecieEntity>()
            val pokeSpecieTypes = mutableListOf<PokeSpecieTypeCrossRef>()
            val pokeTypeEntities = HashMap<Int, PokeTypeEntity>()

            for (item in pokeSpeciesResponse) {
                val id = Utils.getIdAtEndFromUrl(item.url)
                if (id > LAST_VALID_POKEMON_NUMBER) {
                    break
                }

                val pokeSpecieItemResponse = pokeManagerApi.getPokemonItemByIdNetwork(id)
                val pokeTypeEntitiesFromSpecie = pokeSpecieItemResponse.types.fromResponseListToPokeTypeEntityList()
                val pokeSpecieItemEntity = pokeSpecieItemResponse.toPokeSpecieEntity()

                pokeSpecieEntities.add(pokeSpecieItemEntity)

                for (pokeType in pokeTypeEntitiesFromSpecie) {
                    pokeTypeEntities[pokeType.pokeTypeId] = pokeType
                    pokeSpecieTypes.add(
                        PokeSpecieTypeCrossRef(
                            pokeSpecieId = pokeSpecieItemEntity.pokeSpecieId,
                            pokeTypeId = pokeType.pokeTypeId
                        )
                    )
                }
            }

            pokeDatabase.withTransaction {
                clearAllRelatedIfNeeded(loadType)

                val keys = getKeys(offset, state, endOfPaginationReached, pokeSpecieEntities, pokeSpeciesResponse)

                insertAllRelated(keys, pokeSpecieEntities, pokeTypeEntities, pokeSpecieTypes)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun insertAllRelated(keys: List<PokeSpecieRemoteKeysEntity>, pokeSpecieEntities: MutableList<PokeSpecieEntity>,
        pokeTypeEntities: HashMap<Int, PokeTypeEntity>, pokeSpecieTypes: MutableList<PokeSpecieTypeCrossRef>
    ) {
        with(pokeDatabase) {
            pokeSpecieRemoteKeysDao().insertAll(keys)
            pokeSpecieDao().insertAll(pokeSpecieEntities)
            pokeTypeDao().insertAll(pokeTypeEntities.values.toList())
            pokeSpecieTypeDao().insertAll(pokeSpecieTypes)
        }
    }

    private fun getKeys(offset: Int, state: PagingState<Int, PokeSpecieWithTypes>, endOfPaginationReached: Boolean,
        pokeSpecieEntities: MutableList<PokeSpecieEntity>, pokeSpeciesResponse: MutableList<PokemonItemFromListResponse>
    ): List<PokeSpecieRemoteKeysEntity> {
        val prevKey = Utils.getPrevKey(offset, state.config.pageSize)
        val nextKey = if (endOfPaginationReached) null else Utils.getNextKeyE(pokeSpecieEntities)
        return pokeSpeciesResponse.map {
            PokeSpecieRemoteKeysEntity(pokeSpecieId = Utils.getIdAtEndFromUrl(it.url), prevKey = prevKey, nextKey = nextKey)
        }
    }

    private suspend fun clearAllRelatedIfNeeded(loadType: LoadType) {
        if (loadType == LoadType.REFRESH) {
            with(pokeDatabase) {
                pokeSpecieRemoteKeysDao().clearRemoteKeys()
                pokeSpecieDao().clearPokeSpecies()
                pokeTypeDao().clearPokeTypes()
                pokeSpecieTypeDao().clearPokeSpecieTypes()
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PokeSpecieWithTypes>
    ): PokeSpecieRemoteKeysEntity? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.pokeSpecie?.pokeSpecieId?.let { repoId ->
                pokeDatabase.pokeSpecieRemoteKeysDao().remoteKeysPokeSpecieId(repoId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PokeSpecieWithTypes>): PokeSpecieRemoteKeysEntity? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                pokeDatabase.pokeSpecieRemoteKeysDao().remoteKeysPokeSpecieId(repo.pokeSpecie.pokeSpecieId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PokeSpecieWithTypes>): PokeSpecieRemoteKeysEntity? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                pokeDatabase.pokeSpecieRemoteKeysDao().remoteKeysPokeSpecieId(repo.pokeSpecie.pokeSpecieId)
            }
    }

    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
}