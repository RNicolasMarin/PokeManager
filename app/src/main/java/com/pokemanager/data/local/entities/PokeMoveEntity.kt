package com.pokemanager.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pokemanager.utils.Constants.POKE_MOVE_TABLE

@Entity(tableName = POKE_MOVE_TABLE)
data class PokeMoveEntity(
    @PrimaryKey var pokeMoveId: Int = 0,
    var name: String = ""
)
