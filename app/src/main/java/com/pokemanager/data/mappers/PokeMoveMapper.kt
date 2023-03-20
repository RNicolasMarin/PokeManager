package com.pokemanager.data.mappers

import com.pokemanager.data.domain.PokeMoveDomain
import com.pokemanager.data.local.entities.PokeMoveEntity
import com.pokemanager.data.remote.responses.MoveNetwork
import com.pokemanager.utils.UrlUtils.getIdAtEndFromUrl

//Object:
//Response -> Domain
fun MoveNetwork.toPokeMoveDomain() = PokeMoveDomain(
    getIdAtEndFromUrl(move.url),
    move.name
)
//Response -> Entity
fun MoveNetwork.toPokeMoveEntity() = PokeMoveEntity(
    getIdAtEndFromUrl(move.url),
    move.name
)
//Entity -> Domain
fun PokeMoveEntity.toPokeMoveDomain() = PokeMoveDomain(
    id = pokeMoveId,
    name = name
)

//List:
//Response -> Domain
fun MutableList<MoveNetwork>.fromResponseListToPokeMoveDomainList() =
    map { it.toPokeMoveDomain() }.toMutableList()
//Response -> Entity
fun MutableList<MoveNetwork>.fromResponseListToPokeMoveEntityList() =
    map { it.toPokeMoveEntity() }.toMutableList()

//Entity -> Domain
fun MutableList<PokeMoveEntity>.fromEntityListToPokeMoveDomainList() =
    map { it.toPokeMoveDomain() }.toMutableList()

//Domain -> Entity
//Domain -> String
fun MutableList<PokeMoveDomain>.fromDomainListToString(): String {
    val moves = StringBuilder()
    val separator = ","
    forEach { moves.append(it.name).append(separator) }
    return moves.removeSuffix(separator).toString()
}
