package com.pokemanager.utils

import com.google.common.truth.Truth.*
import com.pokemanager.data.domain.EvolutionChainMemberDomain
import com.pokemanager.data.domain.PokeAbilityDomain
import com.pokemanager.data.domain.PokeSpecieDetailDomain
import com.pokemanager.data.domain.PokeStatDomain
import com.pokemanager.ui.species.detail.tabs.EvolutionCase.*
import com.pokemanager.ui.species.detail.tabs.EvolutionRow.*
import com.pokemanager.ui.species.detail.tabs.EvolutionRowSpecie
import com.pokemanager.ui.species.detail.tabs.EvolutionToShow
import com.pokemanager.ui.species.detail.NamesToShow
import com.pokemanager.utils.Constants.STAT_ATTACK
import com.pokemanager.utils.Constants.STAT_DEFENSE
import com.pokemanager.utils.Constants.STAT_HP
import com.pokemanager.utils.Constants.STAT_SPEED
import com.pokemanager.utils.VisualUtils.convertAbilities
import com.pokemanager.utils.VisualUtils.convertHeight
import com.pokemanager.utils.VisualUtils.convertName
import com.pokemanager.utils.VisualUtils.convertWeight
import com.pokemanager.utils.VisualUtils.getDownloadPercentage
import com.pokemanager.utils.VisualUtils.getEvolutionToShow
import com.pokemanager.utils.VisualUtils.getNamesByLanguage
import com.pokemanager.utils.VisualUtils.getStatFromList
import org.junit.Test

class VisualUtilsTest {

    companion object {
        const val percentageSymbol: String = "%"
        const val doneText: String = "Done"

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
        const val DEFAULT_IMAGE_URL = "DEFAULT_IMAGE_URL"
        const val DEFAULT_CHAIN_ID = 0
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


    //getEvolutionToShow
    @Test
    fun `getEvolutionToShow one one one`() {
        val chain = EvolutionChainMemberDomain(
            4,
            "charmander",
            mutableListOf(
                EvolutionChainMemberDomain(
                    5,
                    "charmeleon",
                    mutableListOf(
                        EvolutionChainMemberDomain(
                            6,
                            "charizard",
                            mutableListOf()
                        )
                    )
                )
            )
        )
        val actual = getEvolutionToShow(chain, 6, DEFAULT_CHAIN_ID, DEFAULT_IMAGE_URL)
        val expected = EvolutionToShow(
            DEFAULT_CHAIN_ID,
            OneOneOne,
            mutableListOf(
                EvolutionRowLeftSide(
                    EvolutionRowSpecie(
                        4,
                        "charmander",
                        "",
                        false
                    ),
                    true
                ),
                EvolutionRowRightSide(
                    EvolutionRowSpecie(
                        5,
                        "charmeleon",
                        "",
                        false
                    )
                ),
                EvolutionRowLeftSide(
                    EvolutionRowSpecie(
                        6,
                        "charizard",
                        DEFAULT_IMAGE_URL,
                        true
                    ),
                    false
                )
            )
        )
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getEvolutionToShow one one two`() {
        val chain = EvolutionChainMemberDomain(
            43,
            "oddish",
            mutableListOf(
                EvolutionChainMemberDomain(
                    44,
                    "gloom",
                    mutableListOf(
                        EvolutionChainMemberDomain(
                            45,
                            "vileplume",
                            mutableListOf()
                        ),
                        EvolutionChainMemberDomain(
                            182,
                            "bellossom",
                            mutableListOf()
                        )
                    )
                )
            )
        )
        val actual = getEvolutionToShow(chain, 44, DEFAULT_CHAIN_ID, DEFAULT_IMAGE_URL)
        val expected = EvolutionToShow(
            DEFAULT_CHAIN_ID,
            OneOneTwo,
            mutableListOf(
                EvolutionRowCenter(
                    EvolutionRowSpecie(
                        43,
                        "oddish",
                        "",
                        false
                    ),
                    hasArrowBelow = true, hasArrowSides = false
                ),
                EvolutionRowCenter(
                    EvolutionRowSpecie(
                        44,
                        "gloom",
                        DEFAULT_IMAGE_URL,
                        true
                    ),
                    hasArrowBelow = false, hasArrowSides = true
                ),
                EvolutionRowBothSides(
                    EvolutionRowSpecie(
                        45,
                        "vileplume",
                        "",
                        false
                    ),
                    EvolutionRowSpecie(
                        182,
                        "bellossom",
                        "",
                        false
                    ),
                    hasArrowLeftBelow = false, hasArrowRightBelow = false
                )
            )
        )
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getEvolutionToShow one two two`() {
        val chain = EvolutionChainMemberDomain(
            265,
            "wurmple",
            mutableListOf(
                EvolutionChainMemberDomain(
                    266,
                    "silcoon",
                    mutableListOf(
                        EvolutionChainMemberDomain(
                            267,
                            "beautifly", mutableListOf()
                        )
                    )
                ),
                EvolutionChainMemberDomain(
                    268,
                    "cascoon",
                    mutableListOf(
                        EvolutionChainMemberDomain(
                            269,
                            "dustox", mutableListOf()
                        )
                    )
                )
            )
        )
        val actual = getEvolutionToShow(chain, 267, DEFAULT_CHAIN_ID, DEFAULT_IMAGE_URL)
        val expected = EvolutionToShow(
            DEFAULT_CHAIN_ID,
            OneTwoTwo,
            mutableListOf(
                EvolutionRowCenter(
                    EvolutionRowSpecie(
                        265,
                        "wurmple",
                        "",
                        false
                    ),
                    hasArrowBelow = false, hasArrowSides = true
                ),
                EvolutionRowBothSides(
                    EvolutionRowSpecie(
                        266,
                        "silcoon",
                        "",
                        false
                    ),
                    EvolutionRowSpecie(
                        268,
                        "cascoon",
                        "",
                        false
                    ),
                    hasArrowLeftBelow = true, hasArrowRightBelow = true
                ),
                EvolutionRowBothSides(
                    EvolutionRowSpecie(
                        267,
                        "beautifly",
                        DEFAULT_IMAGE_URL,
                        true
                    ),
                    EvolutionRowSpecie(
                        269,
                        "dustox",
                        "",
                        false
                    ),
                    hasArrowLeftBelow = false, hasArrowRightBelow = false
                )
            )
        )
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getEvolutionToShow one three`() {
        val chain = EvolutionChainMemberDomain(
            236,
            "tyrogue",
            mutableListOf(
                EvolutionChainMemberDomain(
                    106,
                    "hitmonlee", mutableListOf()
                ),
                EvolutionChainMemberDomain(
                    107,
                    "hitmonchan", mutableListOf()
                ),
                EvolutionChainMemberDomain(
                    237,
                    "hitmontop", mutableListOf()
                )
            )
        )
        val actual = getEvolutionToShow(chain, 237, DEFAULT_CHAIN_ID, DEFAULT_IMAGE_URL)
        val expected = EvolutionToShow(
            DEFAULT_CHAIN_ID,
            OneThree,
            mutableListOf(
                EvolutionRowCenter(
                    EvolutionRowSpecie(
                        236,
                        "tyrogue",
                        "",
                        false
                    ),
                    hasArrowBelow = true, hasArrowSides = true
                ),
                EvolutionRowBothSides(
                    EvolutionRowSpecie(
                        106,
                        "hitmonlee",
                        "",
                        false
                    ),
                    EvolutionRowSpecie(
                        107,
                        "hitmonchan",
                        "",
                        false
                    ),
                    hasArrowLeftBelow = false, hasArrowRightBelow = false
                ),
                EvolutionRowCenter(
                    EvolutionRowSpecie(
                        237,
                        "hitmontop",
                        DEFAULT_IMAGE_URL,
                        true
                    ),
                    hasArrowBelow = false, hasArrowSides = false
                )
            )
        )
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getEvolutionToShow one many`() {
        val chain = EvolutionChainMemberDomain(
            133,
            "eevee",
            mutableListOf(
                EvolutionChainMemberDomain(
                    134, "vaporeon", mutableListOf()
                ),
                EvolutionChainMemberDomain(
                    135, "jolteon", mutableListOf()
                ),
                EvolutionChainMemberDomain(
                    136, "flareon", mutableListOf()
                ),
                EvolutionChainMemberDomain(
                    196, "espeon", mutableListOf()
                ),
                EvolutionChainMemberDomain(
                    197, "umbreon", mutableListOf()
                ),
                EvolutionChainMemberDomain(
                    470, "leafeon", mutableListOf()
                ),
                EvolutionChainMemberDomain(
                    471, "glaceon", mutableListOf()
                ),
                EvolutionChainMemberDomain(
                    700, "sylveon", mutableListOf()
                )
            )
        )

        val actual = getEvolutionToShow(chain, 136, DEFAULT_CHAIN_ID, DEFAULT_IMAGE_URL)
        val expected = EvolutionToShow(
            DEFAULT_CHAIN_ID,
            OneMany,
            mutableListOf(
                EvolutionRowCenter(
                    EvolutionRowSpecie(
                        133,
                        "eevee",
                        "",
                        false
                    ),
                    hasArrowBelow = true, hasArrowSides = false
                ),
                EvolutionRowBothSides(
                    EvolutionRowSpecie(
                        134,
                        "vaporeon",
                        "",
                        false
                    ),
                    EvolutionRowSpecie(
                        135,
                        "jolteon",
                        "",
                        false
                    ),
                    hasArrowLeftBelow = false, hasArrowRightBelow = false
                ),
                EvolutionRowBothSides(
                    EvolutionRowSpecie(
                        136,
                        "flareon",
                        DEFAULT_IMAGE_URL,
                        true
                    ),
                    EvolutionRowSpecie(
                        196,
                        "espeon",
                        "",
                        false
                    ),
                    hasArrowLeftBelow = false, hasArrowRightBelow = false
                ),
                EvolutionRowBothSides(
                    EvolutionRowSpecie(
                        197,
                        "umbreon",
                        "",
                        false
                    ),
                    EvolutionRowSpecie(
                        470,
                        "leafeon",
                        "",
                        false
                    ),
                    hasArrowLeftBelow = false, hasArrowRightBelow = false
                ),
                EvolutionRowBothSides(
                    EvolutionRowSpecie(
                        471,
                        "glaceon",
                        "",
                        false
                    ),
                    EvolutionRowSpecie(
                        700,
                        "sylveon",
                        "",
                        false
                    ),
                    hasArrowLeftBelow = false, hasArrowRightBelow = false
                ),
            )
        )
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getEvolutionToShow one two`() {
        val chain = EvolutionChainMemberDomain(
            79,
            "slowpoke",
            mutableListOf(
                EvolutionChainMemberDomain(
                    80,
                    "slowbro", mutableListOf()
                ),
                EvolutionChainMemberDomain(
                    199,
                    "slowking", mutableListOf()
                )
            )
        )

        val actual = getEvolutionToShow(chain, 199, DEFAULT_CHAIN_ID, DEFAULT_IMAGE_URL)
        val expected = EvolutionToShow(
            DEFAULT_CHAIN_ID,
            OneTwo,
            mutableListOf(
                EvolutionRowCenter(
                    EvolutionRowSpecie(
                        79,
                        "slowpoke",
                        "",
                        false
                    ),
                    hasArrowBelow = false, hasArrowSides = true
                ),
                EvolutionRowBothSides(
                    EvolutionRowSpecie(
                        80,
                        "slowbro",
                        "",
                        false
                    ),
                    EvolutionRowSpecie(
                        199,
                        "slowking",
                        DEFAULT_IMAGE_URL,
                        true
                    ),
                    hasArrowLeftBelow = false, hasArrowRightBelow = false
                )
            )
        )
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getEvolutionToShow one one`() {
        val chain = EvolutionChainMemberDomain(
            19,
            "rattata",
            mutableListOf(
                EvolutionChainMemberDomain(
                    20,
                    "raticate", mutableListOf()
                )
            )
        )

        val actual = getEvolutionToShow(chain, 19, DEFAULT_CHAIN_ID, DEFAULT_IMAGE_URL)
        val expected = EvolutionToShow(
            DEFAULT_CHAIN_ID,
            OneOne,
            mutableListOf(
                EvolutionRowCenter(
                    EvolutionRowSpecie(
                        19,
                        "rattata",
                        DEFAULT_IMAGE_URL,
                        true
                    ),
                    hasArrowBelow = true, hasArrowSides = false
                ),
                EvolutionRowCenter(
                    EvolutionRowSpecie(
                        20,
                        "raticate",
                        "",
                        false
                    ),
                    hasArrowBelow = false, hasArrowSides = false
                )
            )
        )
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `getEvolutionToShow one`() {
        val chain = EvolutionChainMemberDomain(
            132, "ditto", mutableListOf()
        )

        val actual = getEvolutionToShow(chain, 132, DEFAULT_CHAIN_ID, DEFAULT_IMAGE_URL)
        val expected = EvolutionToShow(
            DEFAULT_CHAIN_ID, One,
            mutableListOf(
                EvolutionRowCenter(
                    EvolutionRowSpecie(
                        132,
                        "ditto",
                        DEFAULT_IMAGE_URL,
                        true
                    ),
                    hasArrowBelow = false, hasArrowSides = false
                )
            )
        )
        assertThat(expected).isEqualTo(actual)
    }

}