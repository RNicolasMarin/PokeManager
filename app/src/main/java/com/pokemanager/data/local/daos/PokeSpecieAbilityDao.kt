package com.pokemanager.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pokemanager.data.local.entities.PokeSpecieAbilityCrossRef

@Dao
interface PokeSpecieAbilityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokeSpecies: List<PokeSpecieAbilityCrossRef>)

    @Query("DELETE FROM pokeSpecieAbilities")
    suspend fun clearPokeSpecieAbilities()
}