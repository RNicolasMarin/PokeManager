package com.pokemanager.utils

import com.google.common.truth.Truth.*
import com.pokemanager.data.base_models.PokeSpecieBase
import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.utils.Utils.getNextKey
import org.junit.Test

class UtilsTest {

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

    /*
    //getPrevKey
    @Test
    fun `getPrevKey offset is starting key`() {

        val actual = Utils.getPrevKey(list, 10)
        val expected = 5
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getPrevKey ensureValidKey is starting key`() {

    }

    @Test
    fun `getPrevKey ensureValidKey is NOT starting key`() {

    }*/
}