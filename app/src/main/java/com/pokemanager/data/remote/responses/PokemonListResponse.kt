package com.pokemanager.data.remote.responses

data class PokemonListResponse(
    var next: String?,
    var previous: String?,
    var results: MutableList<PokemonItemFromListResponse> = mutableListOf()
)

data class PokemonItemFromListResponse(
    var name: String?,
    var url: String?
)