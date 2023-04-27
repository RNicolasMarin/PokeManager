package com.pokemanager.data.mappers

import com.pokemanager.data.domain.PokeAbilityDomain
import com.pokemanager.data.local.entities.PokeAbilityEntity
import com.pokemanager.data.remote.responses.AbilityNetwork
import com.pokemanager.utils.UrlUtils.getIdAtEndFromUrl

//Object:
//Response -> Domain
fun AbilityNetwork.toPokeAbilityDomain() = PokeAbilityDomain(
    getIdAtEndFromUrl(ability.url),
    ability.name,
    isHidden
)
//Response -> Entity
fun AbilityNetwork.toPokeAbilityEntity() = PokeAbilityEntity(
    getIdAtEndFromUrl(ability.url),
    ability.name,
    isHidden
)
//Entity -> Domain
fun PokeAbilityEntity.toPokeAbilityDomain() = PokeAbilityDomain(
    id = pokeAbilityId,
    name = name
)
//Domain -> Entity

//List:
//Response -> Domain
fun MutableList<AbilityNetwork>.fromResponseListToPokeAbilityDomainList() =
    map { it.toPokeAbilityDomain() }.toMutableList()

//Response -> Entity
fun MutableList<AbilityNetwork>.fromResponseListToPokeAbilityEntityList() =
    map { it.toPokeAbilityEntity() }.toMutableList()
//Entity -> Domain
fun MutableList<PokeAbilityEntity>.fromEntityListToPokeAbilityDomainList() =
    map { it.toPokeAbilityDomain() }.toMutableList()

//Domain -> Entity