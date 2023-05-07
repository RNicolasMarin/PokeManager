package com.pokemanager.utils

import com.pokemanager.data.domain.*
import com.pokemanager.ui.species.detail.*
import com.pokemanager.ui.species.detail.tabs.EvolutionCase
import com.pokemanager.ui.species.detail.tabs.EvolutionCase.*
import com.pokemanager.ui.species.detail.tabs.EvolutionRow
import com.pokemanager.ui.species.detail.tabs.EvolutionRow.*
import com.pokemanager.ui.species.detail.tabs.EvolutionRowSpecie
import com.pokemanager.ui.species.detail.tabs.EvolutionToShow

object VisualUtils {

    /**
     * Expected values and results
     * 69	6,9 kg
     * 905	90,5 kg
     * 3520	352,0 kg
     * **/
    fun convertWeight(weight: Int): String {
        val weightDouble: Double = weight.toDouble() / 10
        var weightConverted = weightDouble.toString()
        weightConverted = weightConverted.replace('.', ',')
        return "$weightConverted kg"
    }

    /**
     * Expected values and results
     * 7	0,7 m
     * 17	1,7 m
     * 45	4,5 m
     * **/
    fun convertHeight(height: Int): String {
        val heightDouble: Double = height.toDouble() / 10
        var heightConverted = heightDouble.toString()
        heightConverted = heightConverted.replace('.', ',')
        return "$heightConverted m"
    }

    fun getDownloadPercentage(progress: Int, total: Int, percentageSymbol: String, doneText: String): String {
        if (progress == 0) return "0$percentageSymbol"
        if (progress == total) return doneText

        val percentage = (progress.toDouble() / total * 100).toInt()
        return "$percentage$percentageSymbol"
    }

    private val allowDash = listOf(250, 474, 718, 782, 783, 784)
    private val replaceWithDotSpace = listOf(122, 866)
    //29                 Nidoran ♀
    //32                 Nidoran ♂
    //772   type-null    Type: Null
    private val replaceWithSpace = listOf(25, 439, 785, 786, 787, 788)

    fun convertName(id: Int, name: String): String {//remove everything after -
        if (id == 772) {
            return "Type: Null"
        }
        if (id == 29 || id == 32) {
            val symbol = if (id == 29) "♀" else "♂"
            return name.substring(0, name.indexOf("-")).firstToUpperCase() + symbol
        }
        if (!name.contains("-")) return name.firstToUpperCase()

        if (allowDash.contains(id)) {
            return replaceBetweenWith(name, "-")
        }
        if (replaceWithDotSpace.contains(id)) {
            return replaceBetweenWith(name,  ". ")
        }
        if (replaceWithSpace.contains(id)) {
            return replaceBetweenWith(name,  " ")
        }
        if (name.contains("-mega")) {
            return secondWordFirstAndChanges(name, "Mega", false)
        }
        if (name.endsWith("-gmax")) {
            return secondWordFirstAndChanges(name, "Gigantamax")
        }
        if (name.endsWith("-alola")) {
            return secondWordFirstAndChanges(name, "Alolan")
        }
        if (name.endsWith("-hisui")) {
            return secondWordFirstAndChanges(name, "Hisuian")
        }
        if (name.endsWith("-galar")) {
            return secondWordFirstAndChanges(name, "Galarian")
        }
        return name.substring(0, name.indexOf("-")).firstToUpperCase()
    }

    private fun secondWordFirstAndChanges(name: String, suffix: String, onlyTwoWords: Boolean = true): String {
        if (onlyTwoWords) {
            return suffix + " " + name.substring(0, name.indexOf("-")).firstToUpperCase()
        }
        val parts = name.split("-")

        val finalPart = StringBuilder()
        parts.forEach {
            if (!it.equals(suffix, true)) {
                finalPart.append(it.firstToUpperCase()).append(" ")
            }
        }
        val nameTransformedString = finalPart.toString()

        return suffix + " " + nameTransformedString.substring(0, nameTransformedString.length-1)
    }

    fun replaceBetweenWith(name: String, replaceFor: String): String {
        val parts = name.split("-")
        val nameTransformed = StringBuilder()
        parts.forEach { nameTransformed.append(it.firstToUpperCase()).append(replaceFor) }
        val nameTransformedString = nameTransformed.toString()
        return nameTransformedString.substring(0, nameTransformedString.length-replaceFor.length)
    }

    fun getNamesByLanguage(
        pokeSpecie: PokeSpecieDetailDomain,
        nameLanguages: NameLanguagesToList
    ): NamesToShow {
        val list = mutableListOf<String>()
        if (nameLanguages.showEnglishName) {
            list.add(convertName(pokeSpecie.id, pokeSpecie.englishName))
        }
        if (nameLanguages.showRoomajiName) {
            list.add(pokeSpecie.japRoomajiName)
        }
        if (nameLanguages.showKanaName) {
            list.add(pokeSpecie.japHrKtName)
        }
        return NamesToShow(
            name1 = if (0 < list.size) list[0] else "",
            name2 = if (1 < list.size) list[1] else "",
            name3 = if (2 < list.size) list[2] else ""
        )
    }

    fun convertAbilities(abilities: MutableList<PokeAbilityDomain>): String {
        if (abilities.isEmpty()) return ""

        val response = StringBuilder()
        abilities.forEach { response.append(replaceBetweenWith(it.name, " ")).append(", ") }
        val responseString = response.toString()
        return responseString.substring(0, response.length-2)
    }

    fun getStatFromList(stats: MutableList<PokeStatDomain>, statToFind: String): String {
        for (stat in stats) {
            if (stat.name == statToFind) {
                return stat.baseStat.toString()
            }
        }
        return ""
    }

    fun getEvolutionToShow(evolutionChain: EvolutionChainMemberDomain, currentId: Int, currentChainId: Int, imageUrl: String): EvolutionToShow? {
        var steps = hashMapOf<Int, MutableList<EvolutionRowSpecie>>()
        steps = getEvolutionSteps(steps, evolutionChain, 0, currentId, imageUrl)

        val size = steps.keys.size
        val case: EvolutionCase = getCase(steps) ?: return null
        val list = mutableListOf<EvolutionRow>()

        val step1 = steps[0] ?: return null
        val rowForStep1 = getRowForStep1(step1, case) ?: return null
        list.add(rowForStep1)

        if (size > 1) {
            val step2 = steps[1] ?: return null
            val rowForStep2 = getRowForStep2(step2, case) ?: return null
            list.addAll(rowForStep2)
        }

        if (size > 2) {
            val step3 = steps[2] ?: return null
            val rowForStep3 = getRowForStep3(step3) ?: return null
            list.addAll(rowForStep3)
        }

        if (list.isEmpty()) return null

        return EvolutionToShow(currentChainId, case, list)
    }

    private fun getRowForStep3(step3: MutableList<EvolutionRowSpecie>): MutableList<EvolutionRow>? {
        val list = mutableListOf<EvolutionRow>()
        var specieLeft : EvolutionRowSpecie? = null
        var specieRight : EvolutionRowSpecie? = null

        for (index in step3.indices) {
            if (index % 2 == 0) {
                specieLeft = step3[index]
            } else {
                specieRight = step3[index]
            }

            if (specieLeft != null) {
                if (specieRight != null) {
                    //row both sides
                    list.add(
                        EvolutionRowBothSides(
                            specieLeft, specieRight
                        )
                    )
                } else if (index == step3.lastIndex) {
                    //row center
                    list.add(
                        EvolutionRowLeftSide(
                            specieLeft
                        )
                    )
                }
            }

            if (index % 2 != 0) {
                specieLeft = null
                specieRight = null
            }
        }
        if (list.isEmpty()) return null
        return list
    }

    private fun getRowForStep2(step2: MutableList<EvolutionRowSpecie>, case: EvolutionCase): MutableList<EvolutionRow>? {
        val list = mutableListOf<EvolutionRow>()
        var specieLeft : EvolutionRowSpecie? = null
        var specieRight : EvolutionRowSpecie? = null
        for (index in step2.indices) {
            if (index % 2 == 0) {
                if (case == OneOneOne) {
                    specieRight = step2[index]
                } else {
                    specieLeft = step2[index]
                }
            } else {
                specieRight = step2[index]
            }

            if (specieLeft != null) {
                if (specieRight != null) {
                    //row both sides
                    val arrowBelow = case == OneTwoTwo
                    list.add(
                        EvolutionRowBothSides(
                            specieLeft, specieRight,
                            hasArrowLeftBelow = arrowBelow,
                            hasArrowRightBelow = arrowBelow
                        )
                    )
                } else if (index == step2.lastIndex) {
                    //row center
                    val arrowSides = case == OneOneTwo
                    list.add(
                        EvolutionRowCenter(
                            specieLeft, hasArrowSides = arrowSides
                        )
                    )
                }
            } else if (specieRight != null) {
                list.add(
                    EvolutionRowRightSide(
                        specieRight
                    )
                )
            }

            if (index % 2 != 0) {
                specieLeft = null
                specieRight = null
            }
        }
        if (list.isEmpty()) return null
        return list
    }

    private fun getRowForStep1(step1: MutableList<EvolutionRowSpecie>, case: EvolutionCase): EvolutionRow? {
        if (step1.isEmpty()) return null

        return if (case == OneOneOne) {
            EvolutionRowLeftSide(
                step1[0],
                isRightArrowOut = true
            )
        } else {
            val withArrowBelow = mutableListOf(OneOneTwo, OneThree, OneMany, OneOne)
            val withArrowSides = mutableListOf(OneTwoTwo, OneTwo, OneThree)
            EvolutionRowCenter(
                step1[0],
                hasArrowBelow = withArrowBelow.contains(case),
                hasArrowSides = withArrowSides.contains(case)
            )
        }
    }

    private fun getCase(steps: HashMap<Int, MutableList<EvolutionRowSpecie>>): EvolutionCase? {
        steps[0] ?: return null
        val size = steps.keys.size
        if (size == 1) return One

        val step2 = steps[1] ?: return null
        if (size == 2) {
            return when (step2.size) {
                1 -> OneOne
                3 -> OneThree
                2 -> OneTwo
                in 3..Int.MAX_VALUE -> OneMany
                else -> null
            }
        }

        val step3 = steps[2] ?: return null
        if (size == 3) {
            if (step2.size == 2) return OneTwoTwo

            return when (step3.size) {
                2 -> OneOneTwo
                1 -> OneOneOne
                else -> null
            }
        }

        return null
    }

    private fun getEvolutionSteps(
        st: HashMap<Int, MutableList<EvolutionRowSpecie>>,
        evolutionChain: EvolutionChainMemberDomain,
        step: Int,
        currentId: Int,
        imageUrl: String
    ): HashMap<Int, MutableList<EvolutionRowSpecie>> {
        var steps = st

        val isSelected = evolutionChain.pokeSpecieId == currentId
        val rowSpecie = EvolutionRowSpecie(
            evolutionChain.pokeSpecieId,
            evolutionChain.pokeSpecieName,
            if (isSelected) imageUrl else "",
            isSelected
        )
        if (steps.contains(step)) {
            //the step exist
            val list = steps[step] ?: mutableListOf()
            list.add(rowSpecie)
            steps[step] = list
        } else {
            //does not exist
            steps[step] = mutableListOf(rowSpecie)
        }

        if (evolutionChain.evolvesTo.isEmpty()) {
            return steps
        }

        for (next in evolutionChain.evolvesTo) {
            steps = getEvolutionSteps(steps, next, step + 1, currentId, imageUrl)
        }

        return steps
    }

    fun updateSelectedEvolutionRowSpecie(evolutionToShow: EvolutionToShow, id: Int): EvolutionToShow {
        for (row in evolutionToShow.evolutionRows) {
            when (row) {
                is EvolutionRowCenter -> {
                    row.rowSpecieCenter.isSelected = row.rowSpecieCenter.id == id
                }
                is EvolutionRowBothSides -> {
                    row.rowSpecieLeft.isSelected = row.rowSpecieLeft.id == id
                    row.rowSpecieRight.isSelected = row.rowSpecieRight.id == id
                }
                is EvolutionRowLeftSide -> {
                    row.rowSpecieLeft.isSelected = row.rowSpecieLeft.id == id
                }
                is EvolutionRowRightSide -> {
                    row.rowSpecieRight.isSelected = row.rowSpecieRight.id == id
                }
            }
        }
        return evolutionToShow
    }
}

