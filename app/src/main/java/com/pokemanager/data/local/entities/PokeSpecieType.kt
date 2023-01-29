package com.pokemanager.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.pokemanager.utils.Constants.POKE_SPECIE_ID
import com.pokemanager.utils.Constants.POKE_SPECIE_TYPE_TABLE
import com.pokemanager.utils.Constants.POKE_TYPE_ID

@Entity(tableName = POKE_SPECIE_TYPE_TABLE, primaryKeys = [POKE_SPECIE_ID, POKE_TYPE_ID])
data class PokeSpecieTypeCrossRef(
    val pokeSpecieId: Int = 0,
    var pokeTypeId: Int = 0,
)

data class PokeSpecieWithTypes(
    @Embedded val pokeSpecie: PokeSpecieEntity,
    @Relation(
        parentColumn = POKE_SPECIE_ID,
        entityColumn = POKE_TYPE_ID,
        associateBy = Junction(PokeSpecieTypeCrossRef::class)
    )
    val pokeTypes: MutableList<PokeTypeEntity>
)
