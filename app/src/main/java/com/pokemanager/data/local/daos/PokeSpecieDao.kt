package com.pokemanager.data.local.daos

import androidx.paging.PagingSource
import androidx.room.*
import com.pokemanager.data.local.entities.PokeSpecieEntity

@Dao
interface PokeSpecieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokeSpecies: List<PokeSpecieEntity>)

    @Query("SELECT * FROM pokeSpecies")
    fun getPokeSpecies(): PagingSource<Int, PokeSpecieEntity>

    @Query("DELETE FROM pokeSpecies")
    suspend fun clearPokeSpecies()

}