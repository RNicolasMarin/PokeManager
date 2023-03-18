package com.pokemanager.utils

import com.pokemanager.data.remote.responses.PokemonSpecieResponse
import com.pokemanager.data.remote.responses.PokemonSpecieDetailResponse
import com.pokemanager.utils.Constants.LAST_VALID_POKEMON_NUMBER
import com.pokemanager.utils.Constants.POKEMON_PAGING_PAGE_SIZE
import kotlin.math.ceil

object Utils {

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


}