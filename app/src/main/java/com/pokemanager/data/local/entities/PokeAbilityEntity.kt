package com.pokemanager.data.local.entities

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.pokemanager.utils.Constants.POKE_ABILITY_TABLE

@Entity(tableName = POKE_ABILITY_TABLE)
data class PokeAbilityEntity(
    @PrimaryKey var pokeAbilityId: Int = 0,
    var name: String = "",
    @Ignore
    var isHidden: Boolean = false
)
