package com.pokemanager.utils

import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.data.local.entities.PokeSpecieDetailEntity
import com.pokemanager.data.remote.responses.PokemonSpecieResponse
import com.pokemanager.data.remote.responses.PokemonSpecieDetailResponse
import com.pokemanager.data.remote.responses.SpriteNetwork
import com.pokemanager.utils.Constants.LAST_VALID_POKEMON_NUMBER
import com.pokemanager.utils.Constants.POKEMON_PAGING_PAGE_SIZE
import java.lang.NumberFormatException
import kotlin.math.ceil
import kotlin.math.max

object Utils {

    fun getIdAtEndFromUrl(url: String): Int {
        var mUrl = url
        val invalidId = 0
        if (mUrl.isBlank()) return invalidId

        if (mUrl.last() == '/') {
            mUrl = mUrl.substring(0, mUrl.lastIndex)
        }

        val slashBeforeIdPosition = mUrl.lastIndexOf('/')
        if (slashBeforeIdPosition == -1 || slashBeforeIdPosition == mUrl.lastIndex) return invalidId

        val possibleId = mUrl.substring(slashBeforeIdPosition + 1, mUrl.length)
        return try {
            possibleId.toInt()
        } catch (e: NumberFormatException) {
            0
        }
    }

    fun getNextKeyD(pokemonList: MutableList<PokeSpecieItemDomain>, lastValidId: Int = LAST_VALID_POKEMON_NUMBER): Int? {
        val last = pokemonList.lastOrNull() ?: return null

        return if (last.id >= lastValidId) {
            null
        } else {
            last.id
        }
    }

    fun getNextKeyE(pokemonList: MutableList<PokeSpecieDetailEntity>): Int? {
        val last = pokemonList.lastOrNull() ?: return null

        return if (last.pokeSpecieId >= LAST_VALID_POKEMON_NUMBER) {
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

    fun getNameByLanguage(language: TextLanguage, pokemonSpecie: PokemonSpecieResponse): String {
        for (name in pokemonSpecie.names) {
            if (name.language.name == language.languageName) {
                return name.name
            }
        }
        return ""
    }

    fun getEntryByLanguage(language: TextLanguage, pokemonSpecie: PokemonSpecieDetailResponse): String {
        for (name in pokemonSpecie.entries) {
            if (name.language.name == language.languageName) {
                return name.flavorText.replace("\n", " ").replace("\\f", " ")
            }
        }
        return ""
    }

    fun getGeneraByLanguage(language: TextLanguage, pokemonSpecie: PokemonSpecieDetailResponse): String {
        for (name in pokemonSpecie.genera) {
            if (name.language.name == language.languageName) {
                return name.genus
            }
        }
        return ""
    }

    /**
     * Expected values and results
     * 69	6,9 kg
     * 905	90,5 kg
     * 3520	352,0 kg
     * **/
    fun convertWeight(weight: Int): String {
        val weightConverted: Double = weight.toDouble() / 10
        return "$weightConverted kg"
    }

    /**
     * Expected values and results
     * 7	0,7 m
     * 17	1,7 m
     * 45	4,5 m
     * **/
    fun convertHeight(height: Int): String {
        val weightConverted: Double = height.toDouble() / 10
        return "$weightConverted m"
    }

    fun getImageUrl(sprites: SpriteNetwork): String {
        val frontDefaultOther = sprites.other.officialArtwork.frontDefault
        if (frontDefaultOther != null) {
            return frontDefaultOther
        }
        val frontShinyOther = sprites.other.officialArtwork.frontShiny
        if (frontShinyOther != null) {
            return frontShinyOther
        }

        val frontDefault = sprites.frontDefault
        if (frontDefault != null) {
            return frontDefault
        }
        val frontShiny = sprites.frontShiny
        if (frontShiny != null) {
            return frontShiny
        }
        return ""
    }
}