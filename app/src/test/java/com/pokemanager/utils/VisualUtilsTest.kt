package com.pokemanager.utils

import com.google.common.truth.Truth.*
import com.pokemanager.data.domain.PokeAbilityDomain
import com.pokemanager.data.domain.PokeSpecieDetailDomain
import com.pokemanager.data.domain.PokeStatDomain
import com.pokemanager.ui.species.NamesToShow
import com.pokemanager.utils.Constants.STAT_ATTACK
import com.pokemanager.utils.Constants.STAT_DEFENSE
import com.pokemanager.utils.Constants.STAT_HP
import com.pokemanager.utils.Constants.STAT_SPEED
import com.pokemanager.utils.VisualUtils.convertAbilities
import com.pokemanager.utils.VisualUtils.convertHeight
import com.pokemanager.utils.VisualUtils.convertName
import com.pokemanager.utils.VisualUtils.convertWeight
import com.pokemanager.utils.VisualUtils.getDownloadPercentage
import com.pokemanager.utils.VisualUtils.getNamesByLanguage
import com.pokemanager.utils.VisualUtils.getStatFromList
import org.junit.Test

class VisualUtilsTest {

    companion object {
        const val percentageSymbol: String = "%"
        const val doneText: String = "Done"

        //escribir los test y seguir con el uso en el viewmodel y la vista
        const val NAME_ENGLISH = "Bulbasaur"
        const val NAME_KANA = "フシギダネ"
        const val NAME_ROOMAJI = "Fushigidane"
        val pokeSpecieForNames = PokeSpecieDetailDomain(
            englishName = NAME_ENGLISH,
            japHrKtName = NAME_KANA,
            japRoomajiName = NAME_ROOMAJI
        )
        val someStats = mutableListOf(
            PokeStatDomain(name = STAT_HP, baseStat = 100),
            PokeStatDomain(name = STAT_ATTACK, baseStat = 150),
            PokeStatDomain(name = STAT_DEFENSE, baseStat = 200),
        )
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

    @Test
    fun `convertName mega`() {
        val actual = convertName(10033, "venusaur-mega")
        val expected = "Mega Venusaur"
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `convertName mega x`() {
        val actual = convertName(10033, "charizard-mega-x")
        val expected = "Mega Charizard X"
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `convertName gmax`() {
        val actual = convertName(10195, "venusaur-gmax")
        val expected = "Gigantamax Venusaur"
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `convertName alola`() {
        val actual = convertName(10100, "raichu-alola")
        val expected = "Alolan Raichu"
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `convertName hisui`() {
        val actual = convertName(10233, "typhlosion-hisui")
        val expected = "Hisuian Typhlosion"
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `convertName galar`() {
        val actual = convertName(10166, "farfetchd-galar")
        val expected = "Galarian Farfetchd"
        assertThat(expected).isEqualTo(actual)
    }

    //getNamesByLanguage
    //e     kana    rom
    //n     n       n
    @Test
    fun `getNamesByLanguage with none`() {
        val languagesToList = NameLanguagesToList(showEnglishName = false, showKanaName = false, showRoomajiName = false)
        val actual = getNamesByLanguage(pokeSpecieForNames, languagesToList)
        val expected = NamesToShow("", "", "")
        assertThat(expected).isEqualTo(actual)
    }

    //n     n       y
    @Test
    fun `getNamesByLanguage with roomaji`() {
        val languagesToList = NameLanguagesToList(showEnglishName = false, showKanaName = false, showRoomajiName = true)
        val actual = getNamesByLanguage(pokeSpecieForNames, languagesToList)
        val expected = NamesToShow(NAME_ROOMAJI, "", "")
        assertThat(expected).isEqualTo(actual)
    }

    //n     y       n
    @Test
    fun `getNamesByLanguage with kana`() {
        val languagesToList = NameLanguagesToList(showEnglishName = false, showKanaName = true, showRoomajiName = false)
        val actual = getNamesByLanguage(pokeSpecieForNames, languagesToList)
        val expected = NamesToShow(NAME_KANA, "", "")
        assertThat(expected).isEqualTo(actual)
    }

    //n     y      y
    @Test
    fun `getNamesByLanguage with kana and roomaji`() {
        val languagesToList = NameLanguagesToList(showEnglishName = false, showKanaName = true, showRoomajiName = true)
        val actual = getNamesByLanguage(pokeSpecieForNames, languagesToList)
        val expected = NamesToShow(NAME_ROOMAJI, NAME_KANA, "")
        assertThat(expected).isEqualTo(actual)
    }

    //y     n       n
    @Test
    fun `getNamesByLanguage with english`() {
        val languagesToList = NameLanguagesToList(showEnglishName = true, showKanaName = false, showRoomajiName = false)
        val actual = getNamesByLanguage(pokeSpecieForNames, languagesToList)
        val expected = NamesToShow(NAME_ENGLISH, "", "")
        assertThat(expected).isEqualTo(actual)
    }

    //y     n      y
    @Test
    fun `getNamesByLanguage with english and roomaji`() {
        val languagesToList = NameLanguagesToList(showEnglishName = true, showKanaName = false, showRoomajiName = true)
        val actual = getNamesByLanguage(pokeSpecieForNames, languagesToList)
        val expected = NamesToShow(NAME_ENGLISH, NAME_ROOMAJI, "")
        assertThat(expected).isEqualTo(actual)
    }

    //y     y       n
    @Test
    fun `getNamesByLanguage with english and kana`() {
        val languagesToList = NameLanguagesToList(showEnglishName = true, showKanaName = true, showRoomajiName = false)
        val actual = getNamesByLanguage(pokeSpecieForNames, languagesToList)
        val expected = NamesToShow(NAME_ENGLISH, NAME_KANA, "")
        assertThat(expected).isEqualTo(actual)
    }

    //y     y      y
    @Test
    fun `getNamesByLanguage with the 3`() {
        val languagesToList = NameLanguagesToList(showEnglishName = true, showKanaName = true, showRoomajiName = true)
        val actual = getNamesByLanguage(pokeSpecieForNames, languagesToList)
        val expected = NamesToShow(NAME_ENGLISH, NAME_ROOMAJI, NAME_KANA)
        assertThat(expected).isEqualTo(actual)
    }


    //convertAbilities
    @Test
    fun `convertAbilities no abilities`() {
        val abilities = mutableListOf<PokeAbilityDomain>()
        val actual = convertAbilities(abilities)
        val expected = ""
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `convertAbilities 1 ability`() {
        val abilities = mutableListOf(
            PokeAbilityDomain(
                id = 14,
                name = "compound-eyes"
            )
        )
        val actual = convertAbilities(abilities)
        val expected = "Compound Eyes"
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `convertAbilities 2 abilities`() {
        val abilities = mutableListOf(
            PokeAbilityDomain(
                id = 14,
                name = "compound-eyes"
            ),
            PokeAbilityDomain(
                id = 110,
                name = "tinted-lens"
            )
        )
        val actual = convertAbilities(abilities)
        val expected = "Compound Eyes, Tinted Lens"
        assertThat(expected).isEqualTo(actual)
    }


    //getStatFromList
    @Test
    fun `getStatFromList stat no founded`() {
        val actual = getStatFromList(someStats, STAT_SPEED)
        val expected = ""
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getStatFromList stat founded`() {
        val actual = getStatFromList(someStats, STAT_HP)
        val expected = "100"
        assertThat(expected).isEqualTo(actual)
    }

}