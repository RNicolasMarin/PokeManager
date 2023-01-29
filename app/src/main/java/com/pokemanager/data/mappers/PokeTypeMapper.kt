package com.pokemanager.data.mappers

import com.pokemanager.data.domain.PokeTypeDomain
import com.pokemanager.data.local.entities.PokeTypeEntity

fun MutableList<PokeTypeEntity>.toPokeTypeDomainList(): MutableList<PokeTypeDomain> {
    val list = map {
        PokeTypeDomain(
            id = it.pokeTypeId,
            name = it.name
        )
    }.toMutableList()
    return list
}