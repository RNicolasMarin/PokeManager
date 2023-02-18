package com.pokemanager.data.mappers

import com.pokemanager.data.domain.PokeTypeDomain
import com.pokemanager.data.local.entities.PokeTypeEntity
import com.pokemanager.data.remote.responses.TypeNetwork
import com.pokemanager.utils.Utils

//Object:
//Response -> Domain
fun TypeNetwork.toPokeTypeDomain() = PokeTypeDomain(
    Utils.getIdAtEndFromUrl(type.url),
    type.name
)
//Response -> Entity
fun TypeNetwork.toPokeTypeEntity() = PokeTypeEntity(
    Utils.getIdAtEndFromUrl(type.url),
    type.name
)
//Entity -> Domain
fun PokeTypeEntity.toPokeTypeDomain() = PokeTypeDomain(
    id = pokeTypeId,
    name = name
)
//Domain -> Entity

//List:
//Response -> Domain
fun MutableList<TypeNetwork>.fromResponseListToPokeTypeDomainList() =
    map { it.toPokeTypeDomain() }.toMutableList()
//Response -> Entity
fun MutableList<TypeNetwork>.fromResponseListToPokeTypeEntityList() =
    map { it.toPokeTypeEntity() }.toMutableList()
//Entity -> Domain
fun MutableList<PokeTypeEntity>.fromEntityListToPokeTypeDomainList() =
    map { it.toPokeTypeDomain() }.toMutableList()

//Domain -> Entity
//Domain -> String
fun MutableList<PokeTypeDomain>.fromDomainListToString(): String {
    val types = StringBuilder()
    val separator = "-"
    forEach { types.append(it.name).append(separator) }
    return types.removeSuffix(separator).toString()
}