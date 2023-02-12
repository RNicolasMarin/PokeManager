package com.pokemanager.data.repositories

import androidx.room.withTransaction
import com.pokemanager.data.local.PokeManagerDatabase
import com.pokemanager.data.local.entities.PokeSpecieEntity
import com.pokemanager.data.local.entities.PokeSpecieRemoteKeysEntity
import com.pokemanager.data.local.entities.PokeSpecieTypeCrossRef
import com.pokemanager.data.local.entities.PokeTypeEntity
import com.pokemanager.data.mappers.fromResponseListToPokeTypeEntityList
import com.pokemanager.data.mappers.toPokeSpecieEntity
import com.pokemanager.data.remote.PokeManagerApi
import com.pokemanager.data.remote.responses.PokemonItemFromListResponse
import com.pokemanager.data.repositories.MainRepository.RequestAndPersistPokeSpeciesResult.*
import com.pokemanager.utils.Constants
import com.pokemanager.utils.Utils
import retrofit2.HttpException
import java.io.IOException

class MainRepository(
    private val pokeManagerApi: PokeManagerApi,
    private val pokeDatabase: PokeManagerDatabase
) {
    suspend fun requestAndPersistPokeSpecies(limit: Int, offset: Int, clearBefore: Boolean): RequestAndPersistPokeSpeciesResult {
        try {
            val pokeSpeciesResponse = pokeManagerApi.getPokemonItemsNetwork(limit, offset).results
            val endOfPaginationReached = pokeSpeciesResponse.isEmpty()

            val pokeSpecieEntities = mutableListOf<PokeSpecieEntity>()
            val pokeSpecieTypes = mutableListOf<PokeSpecieTypeCrossRef>()
            val pokeTypeEntities = HashMap<Int, PokeTypeEntity>()

            for (item in pokeSpeciesResponse) {
                val id = Utils.getIdAtEndFromUrl(item.url)
                if (id > Constants.LAST_VALID_POKEMON_NUMBER) {
                    break
                }

                val pokemonResponse = pokeManagerApi.getPokemonItemByIdNetwork(id)
                val pokemonSpecieResponse = pokeManagerApi.getPokemonSpecieItemByIdNetwork(id)
                val pokeTypeEntitiesFromSpecie = pokemonResponse.types.fromResponseListToPokeTypeEntityList()
                val pokeSpecieItemEntity = pokemonResponse.toPokeSpecieEntity(pokemonSpecieResponse)

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
                clearAllRelatedIfNeeded(clearBefore)

                val keys = getKeys(offset, limit, endOfPaginationReached, pokeSpecieEntities, pokeSpeciesResponse)

                insertAllRelated(keys, pokeSpecieEntities, pokeTypeEntities, pokeSpecieTypes)
            }
            return Success(endOfPaginationReached)
        } catch (exception: IOException) {
            return Error(throwable = exception)
        } catch (exception: HttpException) {
            return Error(throwable = exception)
        }
    }

    private suspend fun clearAllRelatedIfNeeded(clearBefore: Boolean) = with(pokeDatabase) {
        if (!clearBefore) return@with

        pokeSpecieRemoteKeysDao().clearRemoteKeys()
        pokeSpecieDao().clearPokeSpecies()
        pokeTypeDao().clearPokeTypes()
        pokeSpecieTypeDao().clearPokeSpecieTypes()
    }

    private fun getKeys(offset: Int, limit: Int, endOfPaginationReached: Boolean,
                        pokeSpecieEntities: MutableList<PokeSpecieEntity>, pokeSpeciesResponse: MutableList<PokemonItemFromListResponse>
    ): List<PokeSpecieRemoteKeysEntity> {
        val prevKey = Utils.getPrevKey(offset, limit)
        val nextKey = if (endOfPaginationReached) null else Utils.getNextKeyE(pokeSpecieEntities)
        return pokeSpeciesResponse.map {
            PokeSpecieRemoteKeysEntity(pokeSpecieId = Utils.getIdAtEndFromUrl(it.url), prevKey = prevKey, nextKey = nextKey)
        }
    }

    private suspend fun insertAllRelated(keys: List<PokeSpecieRemoteKeysEntity>, pokeSpecieEntities: MutableList<PokeSpecieEntity>,
                                         pokeTypeEntities: HashMap<Int, PokeTypeEntity>, pokeSpecieTypes: MutableList<PokeSpecieTypeCrossRef>
    ) = with(pokeDatabase) {
        pokeSpecieRemoteKeysDao().insertAll(keys)
        pokeSpecieDao().insertAll(pokeSpecieEntities)
        pokeTypeDao().insertAll(pokeTypeEntities.values.toList())
        pokeSpecieTypeDao().insertAll(pokeSpecieTypes)
    }

    sealed class RequestAndPersistPokeSpeciesResult {
        data class Success(val isFinish: Boolean): RequestAndPersistPokeSpeciesResult()
        data class Error(val throwable: Throwable): RequestAndPersistPokeSpeciesResult()
    }

    suspend fun remoteKeysPokeSpecieId(pokeSpecieId: Int) =
        pokeDatabase.pokeSpecieRemoteKeysDao().remoteKeysPokeSpecieId(pokeSpecieId)

    fun getPokeSpeciesWithTypes() =
        pokeDatabase.pokeSpecieTypeDao().getPokeSpeciesWithTypes()

    fun getPokeSpeciesWithTypes(limit: Int, offset: Int) =
        pokeDatabase.pokeSpecieTypeDao().getPokeSpeciesWithTypes(limit, offset)

    fun getPokeSpeciesLastId() =
        pokeDatabase.pokeSpecieTypeDao().getPokeSpeciesLastId()

    suspend fun getPokemonItemsNetwork(limit: Int, offset: Int) =
        pokeManagerApi.getPokemonItemsNetwork(limit, offset)

    suspend fun getPokemonItemByIdNetwork(id: Int) =
        pokeManagerApi.getPokemonItemByIdNetwork(id)

    suspend fun getPokemonSpecieItemByIdNetwork(id: Int) =
        pokeManagerApi.getPokemonSpecieItemByIdNetwork(id)

}