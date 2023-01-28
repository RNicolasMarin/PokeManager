package com.pokemanager.data.remote.responses

import com.google.gson.annotations.SerializedName

data class PokemonItemResponse(
    var id: Int = 0,
    var is_default: Boolean = true,
    var name: String = "",
    var sprites: SpriteNetwork = SpriteNetwork(),
    var types: MutableList<TypeNetwork> = mutableListOf()
)

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
    var type: TypeInsideTypeNetwork?
)

data class TypeInsideTypeNetwork(
    var name: String?,
    var url: String?
)