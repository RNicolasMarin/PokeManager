package com.pokemanager.utils

import com.google.common.truth.Truth.assertThat
import com.pokemanager.data.remote.responses.*
import com.pokemanager.utils.ResponseUtils.getEntryByLanguage
import com.pokemanager.utils.ResponseUtils.getNameByLanguage
import com.pokemanager.utils.TextLanguage.ENGLISH
import com.pokemanager.utils.TextLanguage.JAP_HR_KT
import org.junit.Test

class ResponseUtilsTest {

    companion object {
        const val NAME_TO_FIND = "NAME_TO_FIND"
        const val WRONG_NAME = "WRONG_NAME"
        const val SPECIE_ENGLISH_NAME = "Bulbasaur"
        const val SPECIE_FLAVOR_TEXT = "A strange seed was\nplanted on its\nback at birth.\\fThe plant sprouts\nand grows with\nthis POKéMON."
        const val SPECIE_FLAVOR_FOUNDED = "A strange seed was planted on its back at birth. The plant sprouts and grows with this POKéMON."
    }

    private val pokemonSpecie = PokemonSpecieDetailResponse(
        names = mutableListOf(
            NameNetwork(
                language = LanguageNetwork(JAP_ROOMAJI_NAME),
                name = "Fushigidane"
            ),
            NameNetwork(
                language = LanguageNetwork(ENGLISH_NAME),
                name = SPECIE_ENGLISH_NAME
            )
        ),
        entries = mutableListOf(
            FlavorTextEntryNetwork(
                language = LanguageNetwork(JAP_ROOMAJI_NAME),
                flavorText = "This would be an entry on Roomaji"
            ),
            FlavorTextEntryNetwork(
                language = LanguageNetwork(ENGLISH_NAME),
                flavorText = SPECIE_FLAVOR_TEXT
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

    //getEntryByLanguage
    @Test
    fun `getEntryByLanguage entry founded`() {
        val language = ENGLISH
        val actual = getEntryByLanguage(language, pokemonSpecie)
        val expected = SPECIE_FLAVOR_FOUNDED
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getEntryByLanguage entry not founded`() {
        val language = JAP_HR_KT
        val actual = getEntryByLanguage(language, pokemonSpecie)
        val expected = ""
        assertThat(expected).isEqualTo(actual)
    }


}