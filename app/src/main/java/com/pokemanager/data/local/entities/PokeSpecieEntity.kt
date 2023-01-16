package com.pokemanager.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokeSpecies")
data class PokeSpecieEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String,
    //var types: MutableList<PokeTypeDomain>?
)
