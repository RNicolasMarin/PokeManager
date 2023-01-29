package com.pokemanager.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pokemanager.data.local.daos.PokeSpecieDao
import com.pokemanager.data.local.daos.PokeSpecieRemoteKeysDao
import com.pokemanager.data.local.daos.PokeSpecieTypeDao
import com.pokemanager.data.local.daos.PokeTypeDao
import com.pokemanager.data.local.entities.PokeSpecieEntity
import com.pokemanager.data.local.entities.PokeSpecieRemoteKeysEntity
import com.pokemanager.data.local.entities.PokeSpecieTypeCrossRef
import com.pokemanager.data.local.entities.PokeTypeEntity
import com.pokemanager.utils.Constants.DB_NAME

@Database(
    entities = [PokeSpecieEntity::class, PokeSpecieRemoteKeysEntity::class,
               PokeTypeEntity::class, PokeSpecieTypeCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class PokeManagerDatabase: RoomDatabase() {
    
    abstract fun pokeSpecieDao(): PokeSpecieDao
    abstract fun pokeSpecieRemoteKeysDao(): PokeSpecieRemoteKeysDao
    abstract fun pokeTypeDao(): PokeTypeDao
    abstract fun pokeSpecieTypeDao(): PokeSpecieTypeDao
    
    companion object {
        @Volatile
        private var INSTANCE: PokeManagerDatabase? = null

        fun getInstance(context: Context): PokeManagerDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PokeManagerDatabase::class.java,
                DB_NAME
            ).build()
    }

}