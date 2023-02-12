package com.pokemanager.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pokemanager.utils.Constants.POKE_SPECIE_TABLE

@Entity(tableName = POKE_SPECIE_TABLE)
data class PokeSpecieEntity(
    @PrimaryKey val pokeSpecieId: Int = 0,
    val englishName: String = "",
    var japHrKtName: String = "",
    var japRoomajiName: String = "",
    val imageUrl: String = ""
)
