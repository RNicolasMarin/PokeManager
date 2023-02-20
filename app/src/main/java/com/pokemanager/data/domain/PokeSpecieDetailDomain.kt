package com.pokemanager.data.domain

data class PokeSpecieDetailDomain(
    var id: Int = 0,
    var englishName: String = "",
    var japHrKtName: String = "",
    var japRoomajiName: String = "",
    var imageUrl: String = "",
    var types: MutableList<PokeTypeDomain> = mutableListOf(),
    var description: String = "",
    var weight: Int = 0,
    var height: Int = 0,
) {
    fun areDetailsEmpty() = description.isBlank()
}
