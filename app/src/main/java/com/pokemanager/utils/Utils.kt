package com.pokemanager.utils

import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.data.local.entities.PokeSpecieEntity
import kotlin.math.max

object Utils {

    fun getIdAtEndFromUrl(url: String?): String {
        if (url == null) return ""

        var betweenSlashes = url.split("/")
        if (betweenSlashes.last().isBlank()) {
            betweenSlashes = betweenSlashes.subList(0, betweenSlashes.lastIndex)
        }
        return betweenSlashes.last()
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
}