package com.pokemanager.utils

import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.data.local.entities.PokeSpecieEntity
import com.pokemanager.data.remote.responses.PokemonSpecieItemResponse
import com.pokemanager.utils.Constants.LAST_VALID_POKEMON_NUMBER
import com.pokemanager.utils.Constants.POKEMON_PAGING_PAGE_SIZE
import kotlin.math.ceil
import kotlin.math.max

object Utils {

    fun getIdAtEndFromUrl(url: String): Int {
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

    fun getNextKeyD(pokemonList: MutableList<PokeSpecieItemDomain>, lastValidId: Int = Constants.LAST_VALID_POKEMON_NUMBER): Int? {
        val last = pokemonList.lastOrNull() ?: return null

        return if (last.id >= lastValidId) {
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

    fun getTotalStepsAtDownloadingAll(): Int {
        val result : Double = LAST_VALID_POKEMON_NUMBER.toDouble() / POKEMON_PAGING_PAGE_SIZE.toDouble()
        return ceil(result).toInt()
    }

    fun getNameByLanguage(language: TextLanguage, pokemonSpecie: PokemonSpecieItemResponse): String {
        for (name in pokemonSpecie.names) {
            if (name.language.name == language.languageName) {
                return name.name
            }
        }
        return ""
    }
}