package com.pokemanager.data.domain

data class PokeSpecieItemDomain(
    var id: Long?,
    var name: String?,
    var imageUrl: String?,
    var types: MutableList<PokeTypeDomain>?
)