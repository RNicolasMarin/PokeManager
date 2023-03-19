package com.pokemanager.utils

import com.google.common.truth.Truth.assertThat
import com.pokemanager.data.remote.responses.LanguageNetwork
import com.pokemanager.data.remote.responses.NameNetwork
import com.pokemanager.data.remote.responses.PokemonSpecieItemResponse
import com.pokemanager.utils.ResponseUtils.getNameByLanguage
import com.pokemanager.utils.TextLanguage.ENGLISH
import com.pokemanager.utils.TextLanguage.JAP_HR_KT
import org.junit.Test

class ResponseUtilsTest {

    companion object {
        const val NAME_TO_FIND = "NAME_TO_FIND"
        const val WRONG_NAME = "WRONG_NAME"
        const val SPECIE_ENGLISH_NAME = "Bulbasaur"
    }

    private val pokemonSpecie = PokemonSpecieItemResponse(
        names = mutableListOf(
            NameNetwork(
                language = LanguageNetwork(JAP_ROOMAJI_NAME),
                name = "Fushigidane"
            ),
            NameNetwork(
                language = LanguageNetwork(ENGLISH_NAME),
                name = SPECIE_ENGLISH_NAME
            )
        )
    )

    //getNameByLanguage
    @Test
    fun `getNameByLanguage name founded`() {
        val language = ENGLISH
        val actual = getNameByLanguage(language, pokemonSpecie)
        val expected = SPECIE_ENGLISH_NAME
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getNameByLanguage name not founded`() {
        val language = JAP_HR_KT
        val actual = getNameByLanguage(language, pokemonSpecie)
        val expected = ""
        assertThat(expected).isEqualTo(actual)
    }

}