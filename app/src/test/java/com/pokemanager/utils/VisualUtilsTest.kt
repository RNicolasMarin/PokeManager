package com.pokemanager.utils

import com.google.common.truth.Truth.*
import com.pokemanager.utils.VisualUtils.convertHeight
import com.pokemanager.utils.VisualUtils.convertName
import com.pokemanager.utils.VisualUtils.convertWeight
import com.pokemanager.utils.VisualUtils.getDownloadPercentage
import org.junit.Test

class VisualUtilsTest {

    companion object {
        const val percentageSymbol: String = "%"
        const val doneText: String = "Done"
    }

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

    //getDownloadPercentage
    @Test
    fun `getDownloadPercentage zero`() {
        val actual = getDownloadPercentage(0, 16, percentageSymbol, doneText)
        val expected = "0$percentageSymbol"
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getDownloadPercentage in progress`() {
        val actual = getDownloadPercentage(4, 16, percentageSymbol, doneText)
        val expected = "25$percentageSymbol"
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getDownloadPercentage done`() {
        val actual = getDownloadPercentage(16, 16, percentageSymbol, doneText)
        val expected = doneText
        assertThat(expected).isEqualTo(actual)
    }

    //convertName
    @Test
    fun `convertName no dash option`() {
        val actual = convertName(1, "bulbasaur")
        val expected = "Bulbasaur"
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `convertName nidoran f option`() {
        val actual = convertName(29, "nidoran-f")
        val expected = "Nidoran♀"
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `convertName nidoran m option`() {
        val actual = convertName(32, "nidoran-m")
        val expected = "Nidoran♂"
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `convertName allow dash option`() {
        val actual = convertName(250, "ho-oh")
        val expected = "Ho-Oh"
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `convertName replace with dot space option`() {
        val actual = convertName(122, "mr-mime")
        val expected = "Mr. Mime"
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `convertName type null`() {
        val actual = convertName(772, "type-null")
        val expected = "Type: Null"
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `convertName replace with space`() {
        val actual = convertName(785, "tapu-koko")
        val expected = "Tapu Koko"
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `convertName remove after dash`() {
        val actual = convertName(386, "deoxys-normal")
        val expected = "Deoxys"
        assertThat(expected).isEqualTo(actual)
    }

}