package com.pokemanager.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pokemanager.data.local.entities.PokeTypeEntity

@Dao
interface PokeTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokeTypes: List<PokeTypeEntity>)

    @Query("DELETE FROM pokeTypes")
    suspend fun clearPokeTypes()
}