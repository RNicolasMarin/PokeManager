package com.pokemanager.data.remote.responses

import com.google.gson.annotations.SerializedName

data class EvolutionChainResponse(
    var id: Int = 0,
    var chain: ChainNetwork = ChainNetwork()
)

data class ChainNetwork(
    var species: SpecieNetwork = SpecieNetwork(),
    @SerializedName("evolves_to")
    var evolvesTo: MutableList<ChainNetwork> = mutableListOf()
)

data class SpecieNetwork(
    var name: String = "",
    var url: String = ""
)
