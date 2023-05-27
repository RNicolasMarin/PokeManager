package com.pokemanager.ui.species.detail.tabs

data class EvolutionToShow(
    val chainId: Int,
    val evolutionCase: EvolutionCase,
    val evolutionRows: List<EvolutionRow>
)

enum class EvolutionCase {
    OneOneOne,//3
    OneOneTwo,//3
    OneTwoTwo,//3
    OneThree,//2
    OneMany,//2
    OneTwo,//2
    OneOne,//2
    One//1
}

sealed class EvolutionRow {

    data class EvolutionRowCenter(
        val rowSpecieCenter: EvolutionRowSpecie,
        val hasArrowBelow: Boolean = false,
        val hasArrowSides: Boolean = false
    ) : EvolutionRow()

    data class EvolutionRowBothSides(
        val rowSpecieLeft: EvolutionRowSpecie,
        val rowSpecieRight: EvolutionRowSpecie,
        val hasArrowLeftBelow: Boolean = false,
        val hasArrowRightBelow: Boolean = false
    ) : EvolutionRow()

    data class EvolutionRowLeftSide(
        val rowSpecieLeft: EvolutionRowSpecie,
        val isRightArrowOut: Boolean = false
    ) : EvolutionRow()

    data class EvolutionRowRightSide(
        val rowSpecieRight: EvolutionRowSpecie
    ) : EvolutionRow()

}

data class EvolutionRowSpecie(
    var id: Int = 0,
    var englishName: String = "",
    var imageUrl: String = "",
    var isSelected: Boolean = false
)