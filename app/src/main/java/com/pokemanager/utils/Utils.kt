package com.pokemanager.utils

import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.data.local.entities.PokeSpecieEntity
import com.pokemanager.data.local.entities.PokeTypeEntity
import com.pokemanager.data.remote.responses.PokemonItemResponse
import kotlin.math.max

object Utils {

    fun getIdAtEndFromUrl(url: String?): Int {
        if (url == null) return 0

        var betweenSlashes = url.split("/")
        if (betweenSlashes.last().isBlank()) {
            betweenSlashes = betweenSlashes.subList(0, betweenSlashes.lastIndex)
        }
        return try {
            betweenSlashes.last().toInt()
        } catch (e: Exception) {
            0
        }

    }

    fun getNextKey(pokemonList: MutableList<PokeSpecieItemDomain>): Int? {
        val last = pokemonList.lastOrNull() ?: return null

        return if (last.id >= Constants.LAST_VALID_POKEMON_NUMBER) {
            null
        } else {
            last.id
        }
    }

    fun getNextKeyE(pokemonList: MutableList<PokeSpecieEntity>): Int? {
        val last = pokemonList.lastOrNull() ?: return null

        return if (last.pokeSpecieId >= Constants.LAST_VALID_POKEMON_NUMBER) {
            null
        } else {
            last.pokeSpecieId
        }
    }

    fun getPrevKey(offset: Int, loadSize: Int): Int? {
        return when (offset) {
            Constants.POKEMON_PAGING_STARTING_KEY -> null
            else -> when (val prevKey = ensureValidKey(key = offset - loadSize)) {
                // We're at the start, there's nothing more to load
                Constants.POKEMON_PAGING_STARTING_KEY -> Constants.POKEMON_PAGING_STARTING_KEY
                else -> prevKey
            }
        }
    }

    private fun ensureValidKey(key: Int) = max(Constants.POKEMON_PAGING_STARTING_KEY, key)

    fun getPokeTypeEntityFromResponse(pokeSpecieItemResponse: PokemonItemResponse): MutableList<PokeTypeEntity> {
        return pokeSpecieItemResponse.types.map {
            PokeTypeEntity(getIdAtEndFromUrl(it.type.url), it.type.name)
        } as MutableList<PokeTypeEntity>
    }
}