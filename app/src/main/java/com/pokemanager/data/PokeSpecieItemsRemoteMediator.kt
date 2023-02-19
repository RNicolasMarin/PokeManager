package com.pokemanager.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.pokemanager.data.local.entities.*
import com.pokemanager.data.repositories.MainRepository
import com.pokemanager.data.repositories.MainRepository.RequestAndPersistPokeSpeciesResult.*
import com.pokemanager.utils.Constants.POKEMON_PAGING_STARTING_KEY

@OptIn(ExperimentalPagingApi::class)
class PokeSpecieItemsRemoteMediator(
    private val mainRepository: MainRepository
) : RemoteMediator<Int, PokeSpecieItemWithTypes>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, PokeSpecieItemWithTypes>): MediatorResult {
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

        val result = mainRepository.requestAndPersistPokeSpecies(state.config.pageSize, offset, loadType == LoadType.REFRESH, true)

        return when (result) {
            is Success -> MediatorResult.Success(endOfPaginationReached = result.isFinish)
            is Error -> MediatorResult.Error(result.throwable)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PokeSpecieItemWithTypes>
    ): PokeSpecieRemoteKeysEntity? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.pokeSpecie?.pokeSpecieId?.let { repoId ->
                mainRepository.remoteKeysPokeSpecieId(repoId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PokeSpecieItemWithTypes>): PokeSpecieRemoteKeysEntity? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                mainRepository.remoteKeysPokeSpecieId(repo.pokeSpecie.pokeSpecieId)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PokeSpecieItemWithTypes>): PokeSpecieRemoteKeysEntity? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                mainRepository.remoteKeysPokeSpecieId(repo.pokeSpecie.pokeSpecieId)
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