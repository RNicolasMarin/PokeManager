package com.pokemanager.data.local.daos

import androidx.paging.PagingSource
import androidx.room.*
import com.pokemanager.data.local.entities.PokeSpecieDetailCompleteEntities
import com.pokemanager.data.local.entities.PokeSpecieTypeCrossRef
import com.pokemanager.data.local.entities.PokeSpecieItemWithTypes

@Dao
interface PokeSpecieTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokeSpecies: List<PokeSpecieTypeCrossRef>)

    @Transaction
    @Query("SELECT * FROM pokeSpecies")
    fun getPokeSpeciesWithTypes(): PagingSource<Int, PokeSpecieItemWithTypes>

    @Transaction
    @Query("SELECT * FROM pokeSpecies WHERE pokeSpecieId > :offset LIMIT :limit")
    fun getPokeSpeciesWithTypes(limit: Int, offset: Int): MutableList<PokeSpecieItemWithTypes>

    @Transaction
    @Query("SELECT * FROM pokeSpecies WHERE pokeSpecieId = :pokeSpecieId")
    suspend fun getPokeSpecieDetailCompleteEntities(pokeSpecieId: Int): PokeSpecieDetailCompleteEntities?

    @Query("SELECT MAX(pokeSpecieId) FROM pokeSpecies")
    fun getPokeSpeciesLastId(): Int?

    @Query("DELETE FROM pokeSpecieTypes")
    suspend fun clearPokeSpecieTypes()
}