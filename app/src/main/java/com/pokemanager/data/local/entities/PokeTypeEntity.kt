package com.pokemanager.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pokemanager.utils.Constants.POKE_TYPE_TABLE

@Entity(tableName = POKE_TYPE_TABLE)
data class PokeTypeEntity(
    @PrimaryKey
    var pokeTypeId: Int = 0,
    var name: String = ""
)
