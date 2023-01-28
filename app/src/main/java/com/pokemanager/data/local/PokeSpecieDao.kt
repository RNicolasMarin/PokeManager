package com.pokemanager.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.pokemanager.data.local.entities.PokeSpecieEntity
import com.pokemanager.data.local.entities.PokeSpecieWithTypes

@Dao
interface PokeSpecieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokeSpecies: List<PokeSpecieEntity>)

    @Query("SELECT * FROM pokeSpecies")
    fun getPokeSpecies(): PagingSource<Int, PokeSpecieEntity>

    @Transaction
    @Query("SELECT * FROM pokeSpecies")
    fun getPokeSpeciesWithTypes(): PagingSource<Int, PokeSpecieWithTypes>

    @Query("DELETE FROM pokeSpecies")
    suspend fun clearPokeSpecies()

}