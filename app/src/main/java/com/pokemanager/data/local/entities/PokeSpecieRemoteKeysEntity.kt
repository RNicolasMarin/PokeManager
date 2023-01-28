package com.pokemanager.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pokemanager.utils.Constants.POKE_SPECIE_REMOTE_KEYS_TABLE

@Entity(tableName = POKE_SPECIE_REMOTE_KEYS_TABLE)
data class PokeSpecieRemoteKeysEntity(
    @PrimaryKey val pokeSpecieId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
