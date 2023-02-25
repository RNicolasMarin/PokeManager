package com.pokemanager.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pokemanager.data.local.entities.EvolutionChainMemberEntity
import com.pokemanager.utils.Constants.EVOLUTION_CHAIN_MEMBER_TABLE

@Dao
interface EvolutionChainMemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(evolutionChainMembers: List<EvolutionChainMemberEntity>)

    @Query("DELETE FROM $EVOLUTION_CHAIN_MEMBER_TABLE")
    suspend fun clearEvolutionChainMembers()
}