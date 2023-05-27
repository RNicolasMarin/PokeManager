package com.pokemanager.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pokemanager.data.local.entities.PokeSpecieMoveCrossRef

@Dao
interface PokeSpecieMoveDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokeSpeciesMoves: List<PokeSpecieMoveCrossRef>)

    @Query("DELETE FROM pokeSpecieMoves")
    suspend fun clearPokeSpecieMoves()
}