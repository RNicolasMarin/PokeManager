package com.pokemanager.utils

import com.pokemanager.data.remote.responses.PokemonSpecieDetailResponse
import com.pokemanager.data.remote.responses.PokemonSpecieResponse

object ResponseUtils {

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
}