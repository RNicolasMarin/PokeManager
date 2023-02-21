package com.pokemanager.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pokemanager.data.local.entities.PokeAbilityEntity

@Dao
interface PokeAbilityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokeTypes: List<PokeAbilityEntity>)

    @Query("DELETE FROM pokeAbilities")
    suspend fun clearPokeAbilities()
}