package com.pokemanager.data.domain

data class PokeSpecieItemDomain(
    var id: Int = 0,
    var englishName: String = "",
    var japHrKtName: String = "",
    var japRoomajiName: String = "",
    var imageUrl: String = "",
    var types: MutableList<PokeTypeDomain> = mutableListOf(),
)