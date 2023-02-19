package com.pokemanager.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pokemanager.utils.Constants

//read and insert
@Entity(tableName = Constants.POKE_SPECIE_TABLE)
data class PokeSpecieDetailEntity(
    @PrimaryKey val pokeSpecieId: Int = 0,
    val englishName: String = "",
    var japHrKtName: String = "",
    var japRoomajiName: String = "",
    val imageUrl: String = "",
    var description: String = ""
)
