package com.pokemanager.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pokemanager.utils.Constants.EVOLUTION_CHAIN_MEMBER_TABLE

@Entity(tableName = EVOLUTION_CHAIN_MEMBER_TABLE)
data class EvolutionChainMemberEntity(
    @PrimaryKey var evolutionChainMemberId: Int = 0, //id from the specie and this entity
    var name: String = "", //name from the specie
    var evolutionChainId: Int = 0, //id from the chain it belongs
    var previousMemberId: Int = 0 //id from the previous specie
)
