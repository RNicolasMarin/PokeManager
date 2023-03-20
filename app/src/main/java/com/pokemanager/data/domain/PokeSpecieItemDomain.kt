package com.pokemanager.data.domain

import com.pokemanager.data.base_models.PokeSpecieBase

data class PokeSpecieItemDomain(
    var id: Int = 0,
    var englishName: String = "",
    var japHrKtName: String = "",
    var japRoomajiName: String = "",
    var imageUrl: String = "",
    var types: MutableList<PokeTypeDomain> = mutableListOf(),
): PokeSpecieBase() {

    override fun getModelId() = id
}