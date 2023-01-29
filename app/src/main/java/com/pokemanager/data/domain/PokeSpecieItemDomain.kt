package com.pokemanager.data.domain

data class PokeSpecieItemDomain(
    var id: Int,
    var name: String?,
    var imageUrl: String?,
    var types: List<PokeTypeDomain> = mutableListOf()
)