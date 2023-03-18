package com.pokemanager.utils

import com.google.common.truth.Truth.*
import com.pokemanager.data.base_models.PokeSpecieBase
import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.utils.Utils.getIdAtEndFromUrl
import com.pokemanager.utils.Utils.getNextKey
import org.junit.Test

class UtilsTest {

    //getIdAtEndFromUrl
    @Test
    fun `getIdAtEndFromUrl empty url`() {
        val url = ""
        val actual = getIdAtEndFromUrl(url)
        val expected = 0
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getIdAtEndFromUrl no slash`() {
        val url = "dgvgfgxdvxd"
        val actual = getIdAtEndFromUrl(url)
        val expected = 0
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getIdAtEndFromUrl no id after slash with slash at end`() {
        val url = "dgvgfgxdvxd//"
        val actual = getIdAtEndFromUrl(url)
        val expected = 0
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getIdAtEndFromUrl no id after slash`() {
        val url = "dgvgfgxdvxd/"
        val actual = getIdAtEndFromUrl(url)
        val expected = 0
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getIdAtEndFromUrl end with slash`() {
        val url = "https://pokeapi.co/api/v2/pokemon-species/6/"
        val actual = getIdAtEndFromUrl(url)
        val expected = 6
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getIdAtEndFromUrl end with number`() {
        val url = "https://pokeapi.co/api/v2/pokemon/100"
        val actual = getIdAtEndFromUrl(url)
        val expected = 100
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getIdAtEndFromUrl no numeric id, end with slash`() {
        val url = "https://pokeapi.co/api/v2/pokemon/earf/"
        val actual = getIdAtEndFromUrl(url)
        val expected = 0
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getIdAtEndFromUrl no numeric id, end with number`() {
        val url = "https://pokeapi.co/api/v2/pokemon/earf"
        val actual = getIdAtEndFromUrl(url)
        val expected = 0
        assertThat(expected).isEqualTo(actual)
    }

    //getNextKey
    @Test
    fun `getNextKey empty list`() {
        val list = mutableListOf<PokeSpecieBase>()
        val actual = getNextKey(list)
        val expected = null
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getNextKey greater or equal`() {
        val list = mutableListOf<PokeSpecieBase>(PokeSpecieItemDomain(id = 10))
        val actual = getNextKey(list, 10)
        val expected = null
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getNextKey lower`() {
        val list = mutableListOf<PokeSpecieBase>(PokeSpecieItemDomain(id = 5))
        val actual = getNextKey(list, 10)
        val expected = 5
        assertThat(actual).isEqualTo(expected)
    }
}