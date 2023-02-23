package com.pokemanager.data.remote.responses

import com.google.gson.annotations.SerializedName

interface PokemonSpecieResponse {
    var names: MutableList<NameNetwork>
}

data class PokemonSpecieDetailResponse(
    override var names: MutableList<NameNetwork> = mutableListOf(),
    @SerializedName("flavor_text_entries")
    var entries: MutableList<FlavorTextEntryNetwork> = mutableListOf(),
    var genera: MutableList<GeneraNetwork> = mutableListOf()
): PokemonSpecieResponse

data class PokemonSpecieItemResponse(
    override var names: MutableList<NameNetwork> = mutableListOf()
): PokemonSpecieResponse

data class NameNetwork(
    var language: LanguageNetwork = LanguageNetwork(),
    var name: String = ""
)

data class LanguageNetwork(
    var name: String = "",
)

data class FlavorTextEntryNetwork(
    @SerializedName("flavor_text")
    val flavorText: String = "",
    var language: LanguageNetwork = LanguageNetwork()
)

//Genera/Category
data class GeneraNetwork(
    val genus: String = "",
    var language: LanguageNetwork = LanguageNetwork()
)