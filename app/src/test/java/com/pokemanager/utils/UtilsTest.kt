package com.pokemanager.utils

import com.google.common.truth.Truth.*
import com.pokemanager.utils.Utils.getIdAtEndFromUrl
import org.junit.Test

class UtilsTest {

    //getIdAtEndFromUrl
    @Test
    fun `empty url`() {
        val url = ""
        val actual = getIdAtEndFromUrl(url)
        val expected = 0
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `no slash`() {
        val url = "dgvgfgxdvxd"
        val actual = getIdAtEndFromUrl(url)
        val expected = 0
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `no id after slash with slash at end`() {
        val url = "dgvgfgxdvxd//"
        val actual = getIdAtEndFromUrl(url)
        val expected = 0
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `no id after slash`() {
        val url = "dgvgfgxdvxd/"
        val actual = getIdAtEndFromUrl(url)
        val expected = 0
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `end with slash`() {
        val url = "https://pokeapi.co/api/v2/pokemon-species/6/"
        val actual = getIdAtEndFromUrl(url)
        val expected = 6
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `end with number`() {
        val url = "https://pokeapi.co/api/v2/pokemon/100"
        val actual = getIdAtEndFromUrl(url)
        val expected = 100
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `no numeric id, end with slash`() {
        val url = "https://pokeapi.co/api/v2/pokemon/earf/"
        val actual = getIdAtEndFromUrl(url)
        val expected = 0
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `no numeric id, end with number`() {
        val url = "https://pokeapi.co/api/v2/pokemon/earf"
        val actual = getIdAtEndFromUrl(url)
        val expected = 0
        assertThat(expected).isEqualTo(actual)
    }


}