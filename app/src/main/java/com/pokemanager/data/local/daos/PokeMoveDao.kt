package com.pokemanager.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pokemanager.data.local.entities.PokeMoveEntity

@Dao
interface PokeMoveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokeMoves: List<PokeMoveEntity>)

    @Query("DELETE FROM pokeMoves")
    suspend fun clearPokeMoves()
}