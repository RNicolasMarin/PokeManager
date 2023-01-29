package com.pokemanager.data.local.daos

import androidx.paging.PagingSource
import androidx.room.*
import com.pokemanager.data.local.entities.PokeSpecieTypeCrossRef
import com.pokemanager.data.local.entities.PokeSpecieWithTypes

@Dao
interface PokeSpecieTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokeSpecies: List<PokeSpecieTypeCrossRef>)

    @Transaction
    @Query("SELECT * FROM pokeSpecies")
    fun getPokeSpeciesWithTypes(): PagingSource<Int, PokeSpecieWithTypes>

    @Query("DELETE FROM pokeSpecieTypes")
    suspend fun clearPokeSpecieTypes()
}