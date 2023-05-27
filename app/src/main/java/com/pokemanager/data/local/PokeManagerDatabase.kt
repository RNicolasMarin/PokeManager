package com.pokemanager.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pokemanager.data.local.daos.*
import com.pokemanager.data.local.entities.*
import com.pokemanager.utils.Constants.DB_NAME

@Database(
    entities = [PokeSpecieDetailEntity::class, PokeSpecieRemoteKeysEntity::class,
               PokeTypeEntity::class, PokeSpecieTypeCrossRef::class,
                PokeAbilityEntity::class, PokeSpecieAbilityCrossRef::class,
                PokeMoveEntity::class, PokeSpecieMoveCrossRef::class, EvolutionChainMemberEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(PokeStatConverter::class)
abstract class PokeManagerDatabase: RoomDatabase() {
    
    abstract fun pokeSpecieDao(): PokeSpecieDao
    abstract fun pokeSpecieRemoteKeysDao(): PokeSpecieRemoteKeysDao

    abstract fun pokeTypeDao(): PokeTypeDao
    abstract fun pokeSpecieTypeDao(): PokeSpecieTypeDao

    abstract fun pokeAbilityDao(): PokeAbilityDao
    abstract fun pokeSpecieAbilityDao(): PokeSpecieAbilityDao

    abstract fun pokeMoveDao(): PokeMoveDao
    abstract fun pokeSpecieMoveDao(): PokeSpecieMoveDao

    abstract fun evolutionChainMemberDao(): EvolutionChainMemberDao
    
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