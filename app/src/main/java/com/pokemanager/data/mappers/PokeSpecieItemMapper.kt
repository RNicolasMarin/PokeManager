package com.pokemanager.data.mappers

import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.data.local.entities.PokeSpecieEntity
import com.pokemanager.data.local.entities.PokeSpecieWithTypes
import com.pokemanager.data.remote.responses.PokemonItemResponse

//Object:
//Response -> Domain
fun PokemonItemResponse.toPokeSpecieItemDomain() = PokeSpecieItemDomain(
    id,
    name,
    sprites.other.officialArtwork.front_default,
    types.fromResponseListToPokeTypeDomainList()
)

//Response -> Entity
fun PokemonItemResponse.toPokeSpecieEntity() = PokeSpecieEntity(
    id,
    name,
    sprites.other.officialArtwork.front_default,
)
//Entity -> Domain
fun PokeSpecieEntity.toPokeSpecieItemDomain() = PokeSpecieItemDomain(
    pokeSpecieId,
    name,
    imageUrl
)
//Domain -> Entity

//List:
//Response -> Domain
//Response -> Entity
//Entity -> Domain
//Domain -> Entity

//PokeSpecieWithTypes -> PokeSpecieItemDomain
fun PokeSpecieWithTypes.toPokeSpecieItemDomain() = pokeSpecie.toPokeSpecieItemDomain().apply {
    types = pokeTypes.fromEntityListToPokeTypeDomainList()
}
fun MutableList<PokeSpecieWithTypes>.fromPokeSpecieWithTypesListToPokeSpecieItemDomainList() =
    map { it.toPokeSpecieItemDomain() }.toMutableList()

