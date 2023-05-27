package com.pokemanager.data.domain

data class EvolutionChainDomain(
    var chainId: Int = 0,
    var chain: EvolutionChainMemberDomain = EvolutionChainMemberDomain()
)

data class EvolutionChainMemberDomain(
    var pokeSpecieId: Int = 0,
    var pokeSpecieName: String = "",
    var evolvesTo: MutableList<EvolutionChainMemberDomain> = mutableListOf()
)
