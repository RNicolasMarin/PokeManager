package com.pokemanager.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pokemanager.data.local.entities.PokeSpecieRemoteKeysEntity

@Dao
interface PokeSpecieRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<PokeSpecieRemoteKeysEntity>)

    @Query("SELECT * FROM poke_specie_remote_keys WHERE pokeSpecieId = :pokeSpecieId")
    suspend fun remoteKeysPokeSpecieId(pokeSpecieId: Int): PokeSpecieRemoteKeysEntity?

    @Query("DELETE FROM poke_specie_remote_keys")
    suspend fun clearRemoteKeys()
}