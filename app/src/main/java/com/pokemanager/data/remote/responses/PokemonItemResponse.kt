package com.pokemanager.data.remote.responses

import com.google.gson.annotations.SerializedName

data class PokemonItemResponse(
    var id: Long?,
    var is_default: Boolean?,
    var name: String?,
    var sprites: SpriteNetwork?,
    var types: MutableList<TypeNetwork>?
)

//Image
data class SpriteNetwork(
    var other: SpriteOtherNetwork?
)

data class SpriteOtherNetwork(
    @SerializedName("official-artwork")
    var officialArtwork: OfficialArtworkNetwork?
)

data class OfficialArtworkNetwork(
    var front_default: String?
)

//Types
data class TypeNetwork(
    var type: TypeInsideTypeNetwork?
)

data class TypeInsideTypeNetwork(
    var name: String?,
    var url: String?
)