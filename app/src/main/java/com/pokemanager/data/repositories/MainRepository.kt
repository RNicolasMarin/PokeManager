package com.pokemanager.data.repositories

import androidx.room.withTransaction
import com.pokemanager.data.domain.PokeSpecieDetailDomain
import com.pokemanager.data.local.PokeManagerDatabase
import com.pokemanager.data.local.entities.*
import com.pokemanager.data.mappers.*
import com.pokemanager.data.remote.PokeManagerApi
import com.pokemanager.data.remote.responses.PokemonDetailResponse
import com.pokemanager.data.remote.responses.PokemonItemFromListResponse
import com.pokemanager.data.remote.responses.PokemonItemResponse
import com.pokemanager.data.remote.responses.PokemonResponse
import com.pokemanager.data.repositories.MainRepository.RequestAndPersistPokeSpeciesResult.*
import com.pokemanager.utils.Constants
import com.pokemanager.utils.Utils
import retrofit2.HttpException
import java.io.IOException

class MainRepository(
    private val api: PokeManagerApi,
    private val db: PokeManagerDatabase
) {

    suspend fun requestAndPersistPokeSpecies(limit: Int, offset: Int, clearBefore: Boolean, onlyItemData: Boolean): RequestAndPersistPokeSpeciesResult {
        try {
            val pokeSpeciesResponse = api.getPokemonItemsNetwork(limit, offset).results
            val endOfPaginationReached = pokeSpeciesResponse.isEmpty()

            val pokeSpecieEntities = mutableListOf<PokeSpecieDetailEntity>()
            val pokeSpecieTypes = mutableListOf<PokeSpecieTypeCrossRef>()
            val pokeTypeEntities = HashMap<Int, PokeTypeEntity>()
            val pokeSpecieAbilities = mutableListOf<PokeSpecieAbilityCrossRef>()
            val pokeAbilityEntities = HashMap<Int, PokeAbilityEntity>()
            val pokeMoveEntities = HashMap<Int, PokeMoveEntity>()
            val pokeSpecieMoves = mutableListOf<PokeSpecieMoveCrossRef>()

            for (item in pokeSpeciesResponse) {
                val id = Utils.getIdAtEndFromUrl(item.url)
                if (id > Constants.LAST_VALID_POKEMON_NUMBER) {
                    break
                }

                val pokemonResponse: PokemonResponse = if (onlyItemData) {
                    api.getPokemonItemByIdNetwork(id)
                } else {
                    api.getPokemonDetailByIdNetwork(id)
                }

                val pokeSpecieDetailEntity: PokeSpecieDetailEntity = if (pokemonResponse is PokemonItemResponse) {
                    val pokemonSpecieResponse = api.getPokemonSpecieItemByIdNetwork(id)
                    pokemonResponse.toPokeSpecieDetailEntity(pokemonSpecieResponse)
                } else {
                    val pokemonSpecieResponse = api.getPokemonSpecieDetailByIdNetwork(id)
                    pokemonResponse.toPokeSpecieDetailEntity(pokemonSpecieResponse)
                }

                pokeSpecieEntities.add(pokeSpecieDetailEntity)

                val pokeTypeEntitiesFromSpecie = pokemonResponse.types.fromResponseListToPokeTypeEntityList()

                for (pokeType in pokeTypeEntitiesFromSpecie) {
                    pokeTypeEntities[pokeType.pokeTypeId] = pokeType
                    pokeSpecieTypes.add(
                        PokeSpecieTypeCrossRef(
                            pokeSpecieId = pokeSpecieDetailEntity.pokeSpecieId,
                            pokeTypeId = pokeType.pokeTypeId
                        )
                    )
                }

                if (pokemonResponse is PokemonDetailResponse) {
                    val pokeAbilitiesEntitiesFromSpecie = pokemonResponse.abilities.fromResponseListToPokeAbilityEntityList()
                    for (pokeAbility in pokeAbilitiesEntitiesFromSpecie) {
                        pokeAbilityEntities[pokeAbility.pokeAbilityId] = pokeAbility
                        pokeSpecieAbilities.add(
                            PokeSpecieAbilityCrossRef(
                                pokeSpecieId = pokeSpecieDetailEntity.pokeSpecieId,
                                pokeAbilityId = pokeAbility.pokeAbilityId,
                                isHidden = pokeAbility.isHidden
                            )
                        )
                    }

                    val pokeMovesEntitiesFromSpecie = pokemonResponse.moves.fromResponseListToPokeMoveEntityList()
                    for (pokeMove in pokeMovesEntitiesFromSpecie) {
                        pokeMoveEntities[pokeMove.pokeMoveId] = pokeMove
                        pokeSpecieMoves.add(
                            PokeSpecieMoveCrossRef(
                                pokeSpecieId = pokeSpecieDetailEntity.pokeSpecieId,
                                pokeMoveId = pokeMove.pokeMoveId
                            )
                        )
                    }
                }
            }

            db.withTransaction {
                clearAllRelatedIfNeeded(clearBefore)

                val keys = getKeys(offset, limit, endOfPaginationReached, pokeSpecieEntities, pokeSpeciesResponse)

                insertAllRelated(keys, pokeSpecieEntities, pokeTypeEntities, pokeSpecieTypes,
                    pokeAbilityEntities, pokeSpecieAbilities, pokeMoveEntities, pokeSpecieMoves)
            }
            return Success(endOfPaginationReached)
        } catch (exception: IOException) {
            return Error(throwable = exception)
        } catch (exception: HttpException) {
            return Error(throwable = exception)
        }
    }

    private suspend fun clearAllRelatedIfNeeded(clearBefore: Boolean) = with(db) {
        if (!clearBefore) return@with

        pokeSpecieRemoteKeysDao().clearRemoteKeys()
        pokeSpecieDao().clearPokeSpecies()
        pokeTypeDao().clearPokeTypes()
        pokeSpecieTypeDao().clearPokeSpecieTypes()

        pokeAbilityDao().clearPokeAbilities()
        pokeSpecieAbilityDao().clearPokeSpecieAbilities()

        pokeMoveDao().clearPokeMoves()
        pokeSpecieMoveDao().clearPokeSpecieMoves()
    }

    private fun getKeys(offset: Int, limit: Int, endOfPaginationReached: Boolean,
                        pokeSpecieEntities: MutableList<PokeSpecieDetailEntity>, pokeSpeciesResponse: MutableList<PokemonItemFromListResponse>
    ): List<PokeSpecieRemoteKeysEntity> {
        val prevKey = Utils.getPrevKey(offset, limit)
        val nextKey = if (endOfPaginationReached) null else Utils.getNextKeyE(pokeSpecieEntities)
        return pokeSpeciesResponse.map {
            PokeSpecieRemoteKeysEntity(pokeSpecieId = Utils.getIdAtEndFromUrl(it.url), prevKey = prevKey, nextKey = nextKey)
        }
    }

    private suspend fun insertAllRelated(
        keys: List<PokeSpecieRemoteKeysEntity>,
        pokeSpecieEntities: MutableList<PokeSpecieDetailEntity>,
        pokeTypeEntities: HashMap<Int, PokeTypeEntity>,
        pokeSpecieTypes: MutableList<PokeSpecieTypeCrossRef>,
        pokeAbilityEntities: HashMap<Int, PokeAbilityEntity>,
        pokeSpecieAbilities: MutableList<PokeSpecieAbilityCrossRef>,
        pokeMoveEntities: HashMap<Int, PokeMoveEntity>,
        pokeSpecieMoves: MutableList<PokeSpecieMoveCrossRef>
    ) = with(db) {
        pokeSpecieRemoteKeysDao().insertAll(keys)
        pokeSpecieDao().insertAll(pokeSpecieEntities)

        pokeTypeDao().insertAll(pokeTypeEntities.values.toList())
        pokeSpecieTypeDao().insertAll(pokeSpecieTypes)

        pokeAbilityDao().insertAll(pokeAbilityEntities.values.toList())
        pokeSpecieAbilityDao().insertAll(pokeSpecieAbilities)

        pokeMoveDao().insertAll(pokeMoveEntities.values.toList())
        pokeSpecieMoveDao().insertAll(pokeSpecieMoves)
    }

    sealed class RequestAndPersistPokeSpeciesResult {
        data class Success(val isFinish: Boolean): RequestAndPersistPokeSpeciesResult()
        data class Error(val throwable: Throwable): RequestAndPersistPokeSpeciesResult()
    }

    suspend fun remoteKeysPokeSpecieId(pokeSpecieId: Int) =
        db.pokeSpecieRemoteKeysDao().remoteKeysPokeSpecieId(pokeSpecieId)

    fun getPokeSpecieItemsWithTypes() =
        db.pokeSpecieTypeDao().getPokeSpeciesWithTypes()

    fun getPokeSpecieItemsWithTypes(limit: Int, offset: Int) =
        db.pokeSpecieTypeDao().getPokeSpeciesWithTypes(limit, offset)

    fun getPokeSpeciesLastId() =
        db.pokeSpecieTypeDao().getPokeSpeciesLastId()

    suspend fun getPokemonItemsNetwork(limit: Int, offset: Int) =
        api.getPokemonItemsNetwork(limit, offset)

    suspend fun getPokemonItemByIdNetwork(id: Int) =
        api.getPokemonItemByIdNetwork(id)

    suspend fun getPokemonDetailByIdNetwork(id: Int) =
        api.getPokemonDetailByIdNetwork(id)

    suspend fun getPokemonSpecieItemByIdNetwork(id: Int) =
        api.getPokemonSpecieItemByIdNetwork(id)

    private suspend fun getPokemonSpecieDetailByIdNetwork(id: Int) =
        api.getPokemonSpecieDetailByIdNetwork(id)


    suspend fun getPokeSpeciesDetailComplete(pokeSpecieId: Int) =
        db.pokeSpecieTypeDao().getPokeSpecieDetailWithTypes(pokeSpecieId)

    suspend fun getPokeSpeciesDetailFromNetwork(id: Int) : PokeSpecieDetailDomain {
        val pokemonResponse = getPokemonDetailByIdNetwork(id)
        val pokemonSpecieResponse = getPokemonSpecieDetailByIdNetwork(id)
        return pokemonResponse.toPokeSpecieDetailDomain(pokemonSpecieResponse)
    }

}