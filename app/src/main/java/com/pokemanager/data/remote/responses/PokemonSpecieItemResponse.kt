package com.pokemanager.data.remote.responses

data class PokemonSpecieItemResponse(
    var names: MutableList<NameNetwork> = mutableListOf()
)

data class NameNetwork(
    var language: LanguageNetwork = LanguageNetwork(),
    var name: String = ""
)

data class LanguageNetwork(
    var name: String = "",
)