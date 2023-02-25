package com.pokemanager.data.mappers

import com.pokemanager.data.domain.EvolutionChainDomain
import com.pokemanager.data.domain.EvolutionChainMemberDomain
import com.pokemanager.data.local.entities.EvolutionChainMemberEntity
import com.pokemanager.data.remote.responses.ChainNetwork
import com.pokemanager.data.remote.responses.EvolutionChainResponse
import com.pokemanager.utils.Constants.LAST_VALID_POKEMON_NUMBER
import com.pokemanager.utils.Utils

//Object:
//Response -> Domain
fun EvolutionChainResponse.toEvolutionChainDomain() = EvolutionChainDomain(
    chainId = id,
    chain = chain.toEvolutionChainMemberDomain()
)

//SUB Object:
//Response -> Domain
fun ChainNetwork.toEvolutionChainMemberDomain() = EvolutionChainMemberDomain(
    pokeSpecieId = Utils.getIdAtEndFromUrl(species.url),
    pokeSpecieName = species.name,
    evolvesTo = evolvesTo.fromResponseListToEvolutionChainMemberDomainList()
)


//SUB Object List:
//Response -> Domain
fun MutableList<ChainNetwork>.fromResponseListToEvolutionChainMemberDomainList(): MutableList<EvolutionChainMemberDomain> =
    map { it.toEvolutionChainMemberDomain() }.toMutableList()

//Entity List -> Domain
fun MutableList<EvolutionChainMemberEntity>.fromEntityListToEvolutionChainDomain(): EvolutionChainDomain {
    if (this.isEmpty()) return EvolutionChainDomain()
    val result = EvolutionChainDomain(this[0].evolutionChainId)
    val chain = this.getEvolutionChainMemberDomainFromEntityList(0)
    if (chain.isNotEmpty()) {
        result.chain = chain[0]
    }
    return result
}

fun MutableList<EvolutionChainMemberEntity>.getEvolutionChainMemberDomainFromEntityList(
    previousId: Int
): MutableList<EvolutionChainMemberDomain> {
    if (isEmpty()) return mutableListOf()

    val evolvesTo = mutableListOf<EvolutionChainMemberDomain>()
    for (member in this) {
        if (member.previousMemberId == previousId) {
            val newList = mutableListOf<EvolutionChainMemberEntity>()
            for (newMember in this) {
                if (newMember != member) {
                    newList.add(newMember)
                }
            }
            evolvesTo.add(EvolutionChainMemberDomain(
                pokeSpecieId = member.evolutionChainMemberId,
                pokeSpecieName = member.name,
                evolvesTo = newList.getEvolutionChainMemberDomainFromEntityList(
                    member.evolutionChainMemberId
                )
            ))
        }
    }
    return evolvesTo
}

//Response -> Entity List
fun EvolutionChainResponse.fromResponseToEvolutionChainMemberEntityList(): MutableList<EvolutionChainMemberEntity> {
    return chain.fromResponseToEvolutionChainMemberEntityList(id, 0)
}

fun ChainNetwork.fromResponseToEvolutionChainMemberEntityList(chainId: Int, previousId: Int): MutableList<EvolutionChainMemberEntity> {
    val specieId = Utils.getIdAtEndFromUrl(species.url)
    if (specieId > LAST_VALID_POKEMON_NUMBER) return mutableListOf()
    val list = mutableListOf<EvolutionChainMemberEntity>()
    list.add(
        EvolutionChainMemberEntity(
            evolutionChainMemberId = specieId,
            name = species.name,
            evolutionChainId = chainId,
            previousMemberId = previousId
        )
    )
    for (member in evolvesTo) {
        list.addAll(member.fromResponseToEvolutionChainMemberEntityList(chainId, specieId))
    }
    return list
}