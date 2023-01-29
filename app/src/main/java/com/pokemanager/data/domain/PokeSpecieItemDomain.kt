package com.pokemanager.data.domain

data class PokeSpecieItemDomain(
    var id: Int = 0,
    var name: String = "",
    var imageUrl: String = "",
    var types: MutableList<PokeTypeDomain> = mutableListOf()
)