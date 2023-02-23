package com.pokemanager.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pokemanager.data.domain.PokeStatDomain
import com.pokemanager.utils.Constants

//read and insert
@Entity(tableName = Constants.POKE_SPECIE_TABLE)
data class PokeSpecieDetailEntity(
    @PrimaryKey val pokeSpecieId: Int = 0,
    val englishName: String = "",
    var japHrKtName: String = "",
    var japRoomajiName: String = "",
    val imageUrl: String = "",
    var description: String = "",
    var weight: Int = 0,
    var height: Int = 0,
    var stats: MutableList<PokeStatDomain> = mutableListOf(),
    var genera: String = ""
)
