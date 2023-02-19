package com.pokemanager.data.local.daos

import androidx.room.*
import com.pokemanager.data.local.entities.PokeSpecieDetailEntity

@Dao
interface PokeSpecieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokeSpecies: List<PokeSpecieDetailEntity>)

    @Query("DELETE FROM pokeSpecies")
    suspend fun clearPokeSpecies()

}