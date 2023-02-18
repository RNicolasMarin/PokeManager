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

    @Transaction
    @Query("SELECT * FROM pokeSpecies WHERE pokeSpecieId > :offset LIMIT :limit")
    fun getPokeSpeciesWithTypes(limit: Int, offset: Int): MutableList<PokeSpecieWithTypes>

    @Transaction
    @Query("SELECT * FROM pokeSpecies WHERE pokeSpecieId = :pokeSpecieId")
    suspend fun getPokeSpecieWithTypes(pokeSpecieId: Int): PokeSpecieWithTypes?

    @Query("SELECT MAX(pokeSpecieId) FROM pokeSpecies")
    fun getPokeSpeciesLastId(): Int?

    @Query("DELETE FROM pokeSpecieTypes")
    suspend fun clearPokeSpecieTypes()
}