package com.pokemanager.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pokemanager.data.local.entities.PokeSpecieEntity
import com.pokemanager.utils.Constants.DB_NAME

@Database(
    entities = [PokeSpecieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PokeManagerDatabase: RoomDatabase() {
    
    abstract fun pokeSpecieDao(): PokeSpecieDao
    
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