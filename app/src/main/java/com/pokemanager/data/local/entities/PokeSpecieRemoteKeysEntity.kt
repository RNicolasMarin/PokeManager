package com.pokemanager.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "poke_specie_remote_keys")
data class PokeSpecieRemoteKeysEntity(
    @PrimaryKey val pokeSpecieId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
