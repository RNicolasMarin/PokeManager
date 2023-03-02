package com.pokemanager.data.remote.responses

import com.google.gson.annotations.SerializedName

interface PokemonResponse {
    var id: Int
    var isDefault: Boolean
    var name: String
    var sprites: SpriteNetwork
    var types: MutableList<TypeNetwork>
}

data class PokemonItemResponse(
    override var id: Int = 0,
    @SerializedName("is_default")
    override var isDefault: Boolean = true,
    override var name: String = "",
    override var sprites: SpriteNetwork = SpriteNetwork(),
    override var types: MutableList<TypeNetwork> = mutableListOf()
): PokemonResponse

data class PokemonDetailResponse(
    override var id: Int = 0,
    @SerializedName("is_default")
    override var isDefault: Boolean = true,
    override var name: String = "",
    override var sprites: SpriteNetwork = SpriteNetwork(),
    override var types: MutableList<TypeNetwork> = mutableListOf(),
    var weight: Int = 0,
    var height: Int = 0,
    var abilities: MutableList<AbilityNetwork> = mutableListOf(),
    var stats: MutableList<StatNetwork> = mutableListOf(),
    var moves: MutableList<MoveNetwork> = mutableListOf(),
): PokemonResponse

//Image
data class SpriteNetwork(
    var other: SpriteOtherNetwork = SpriteOtherNetwork(),
    @SerializedName("front_default")
    var frontDefault: String? = "",
    @SerializedName("front_shiny")
    var frontShiny: String? = ""
)

data class SpriteOtherNetwork(
    @SerializedName("official-artwork")
    var officialArtwork: OfficialArtworkNetwork = OfficialArtworkNetwork()
)

data class OfficialArtworkNetwork(
    @SerializedName("front_default")
    var frontDefault: String? = "",
    @SerializedName("front_shiny")
    var frontShiny: String? = ""
)

//Types
data class TypeNetwork(
    var type: TypeTypeNetwork = TypeTypeNetwork()
)

data class TypeTypeNetwork(
    var name: String = "",
    var url: String = ""
)

//Abilities
data class AbilityNetwork(
    val ability: AbilityAbilityNetwork = AbilityAbilityNetwork(),
    @SerializedName("is_hidden")
    val isHidden: Boolean = false
)

data class AbilityAbilityNetwork(
    val name: String = "",
    val url: String = "",
)

//Stats
data class StatNetwork(
    @SerializedName("base_stat")
    val baseStat: Int = 0,
    val effort: Int = 0,
    val stat: StatStatNetwork = StatStatNetwork()
)

data class StatStatNetwork(
    val name: String = "",
    val url: String = "",
)

//Moves
data class MoveNetwork(
    val move: MoveMoveNetwork = MoveMoveNetwork()
)

data class MoveMoveNetwork(
    val name: String = "",
    val url: String = "",
)