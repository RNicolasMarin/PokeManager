package com.pokemanager.data.remote.item

data class PokemonNetworkItem(
    var id: Long?,
    var is_default: Boolean?,
    var name: String?,
    var sprites: Sprite?,
    var types: MutableList<Type>?
)

data class Sprite(
    var other: SpriteOther?
)

data class SpriteOther(
    var officialArtwork: OfficialArtwork?//official-artwork
)

data class OfficialArtwork(
    var front_default: String?
)


data class Type(
    var type: TypeInsideType?
)

data class TypeInsideType(
    var name: String?,
    var url: String?
)