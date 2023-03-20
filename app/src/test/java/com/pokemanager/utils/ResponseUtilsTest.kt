package com.pokemanager.utils

import com.google.common.truth.Truth.assertThat
import com.pokemanager.data.remote.responses.*
import com.pokemanager.utils.ResponseUtils.getEntryByLanguage
import com.pokemanager.utils.ResponseUtils.getGeneraByLanguage
import com.pokemanager.utils.ResponseUtils.getNameByLanguage
import com.pokemanager.utils.TextLanguage.ENGLISH
import com.pokemanager.utils.TextLanguage.JAP_HR_KT
import org.junit.Test

class ResponseUtilsTest {

    companion object {
        const val SPECIE_ENGLISH_NAME = "Bulbasaur"
        const val SPECIE_ENGLISH_FLAVOR = "A strange seed was\nplanted on its\nback at birth.\\fThe plant sprouts\nand grows with\nthis POKéMON."
        const val SPECIE_ENGLISH_FLAVOR_FOUNDED = "A strange seed was planted on its back at birth. The plant sprouts and grows with this POKéMON."
        const val SPECIE_ENGLISH_GENERA = "Seed Pokémon"
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
                flavorText = SPECIE_ENGLISH_FLAVOR
            )
        ),
        genera = mutableListOf(
            GeneraNetwork(
                language = LanguageNetwork(JAP_ROOMAJI_NAME),
                genus = "This would be a genera on Roomaji"
            ),
            GeneraNetwork(
                language = LanguageNetwork(ENGLISH_NAME),
                genus = SPECIE_ENGLISH_GENERA
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
        val expected = SPECIE_ENGLISH_FLAVOR_FOUNDED
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getEntryByLanguage entry not founded`() {
        val language = JAP_HR_KT
        val actual = getEntryByLanguage(language, pokemonSpecie)
        val expected = ""
        assertThat(expected).isEqualTo(actual)
    }

    //getGeneraByLanguage
    @Test
    fun `getGeneraByLanguage genera founded`() {
        val language = ENGLISH
        val actual = getGeneraByLanguage(language, pokemonSpecie)
        val expected = SPECIE_ENGLISH_GENERA
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getGeneraByLanguage genera not founded`() {
        val language = JAP_HR_KT
        val actual = getGeneraByLanguage(language, pokemonSpecie)
        val expected = ""
        assertThat(expected).isEqualTo(actual)
    }

}