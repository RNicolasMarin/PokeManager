package com.pokemanager.data.local.daos

import androidx.room.*
import com.pokemanager.data.local.entities.PokeSpecieDetailEntity
import com.pokemanager.utils.Constants.POKE_SPECIE_TABLE

@Dao
interface PokeSpecieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokeSpecies: List<PokeSpecieDetailEntity>)

    @Query("DELETE FROM pokeSpecies")
    suspend fun clearPokeSpecies()

    @Query("SELECT imageUrl FROM $POKE_SPECIE_TABLE WHERE pokeSpecieId = :id")
    suspend fun getImageUrlForSpecieLocal(id: Int): String

}