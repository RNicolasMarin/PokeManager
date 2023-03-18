package com.pokemanager.utils

import com.google.common.truth.Truth.*
import com.pokemanager.utils.VisualUtils.convertHeight
import com.pokemanager.utils.VisualUtils.convertWeight
import org.junit.Test

class VisualUtilsTest {

    //convertWeight
    @Test
    fun `convertWeight 2 digits`() {
        val actual = convertWeight(69)
        val expected = "6,9 kg"
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `convertWeight 3 digits`() {
        val actual = convertWeight(905)
        val expected = "90,5 kg"
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `convertWeight 4 digits`() {
        val actual = convertWeight(3520)
        val expected = "352,0 kg"
        assertThat(expected).isEqualTo(actual)
    }

    //convertHeight
    @Test
    fun `convertHeight 1 digits`() {
        val actual = convertHeight(7)
        val expected = "0,7 m"
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `convertHeight 2 digits`() {
        val actual = convertHeight(17)
        val expected = "1,7 m"
        assertThat(expected).isEqualTo(actual)
    }
}