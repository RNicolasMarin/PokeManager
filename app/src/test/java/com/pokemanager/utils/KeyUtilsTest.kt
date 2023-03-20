package com.pokemanager.utils

import com.google.common.truth.Truth.*
import com.pokemanager.data.base_models.PokeSpecieBase
import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.utils.Constants.POKEMON_PAGING_STARTING_KEY
import com.pokemanager.utils.KeyUtils.getNextKey
import com.pokemanager.utils.KeyUtils.getPrevKey
import org.junit.Test

class KeyUtilsTest {

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

    //getPrevKey
    @Test
    fun `getPrevKey offset is starting key`() {
        val actual = getPrevKey(POKEMON_PAGING_STARTING_KEY, 10)
        val expected = null
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getPrevKey ensureValidKey is starting key`() {
        val actual = getPrevKey(10, 10)
        val expected = POKEMON_PAGING_STARTING_KEY
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `getPrevKey ensureValidKey is NOT starting key`() {
        val actual = getPrevKey(12, 10)
        val expected = 2
        assertThat(actual).isEqualTo(expected)
    }

}