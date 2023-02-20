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
): PokemonResponse

//Image
data class SpriteNetwork(
    var other: SpriteOtherNetwork = SpriteOtherNetwork()
)

data class SpriteOtherNetwork(
    @SerializedName("official-artwork")
    var officialArtwork: OfficialArtworkNetwork = OfficialArtworkNetwork()
)

data class OfficialArtworkNetwork(
    var front_default: String = ""
)

//Types
data class TypeNetwork(
    var type: TypeInsideTypeNetwork = TypeInsideTypeNetwork()
)

data class TypeInsideTypeNetwork(
    var name: String = "",
    var url: String = ""
)