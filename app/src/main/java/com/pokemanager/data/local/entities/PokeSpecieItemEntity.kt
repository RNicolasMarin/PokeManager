package com.pokemanager.data.local.entities

import androidx.room.PrimaryKey

//only read
data class PokeSpecieItemEntity(
    @PrimaryKey val pokeSpecieId: Int = 0,
    val englishName: String = "",
    var japHrKtName: String = "",
    var japRoomajiName: String = "",
    val imageUrl: String = "",
)
