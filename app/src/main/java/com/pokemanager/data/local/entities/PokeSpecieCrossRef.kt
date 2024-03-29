package com.pokemanager.data.local.entities

import androidx.room.*
import com.pokemanager.utils.Constants.EVOLUTION_CHAIN_ID
import com.pokemanager.utils.Constants.POKE_ABILITY_ID
import com.pokemanager.utils.Constants.POKE_MOVE_ID
import com.pokemanager.utils.Constants.POKE_SPECIE_ABILITY_TABLE
import com.pokemanager.utils.Constants.POKE_SPECIE_EVOLUTION_CHAIN_ID
import com.pokemanager.utils.Constants.POKE_SPECIE_ID
import com.pokemanager.utils.Constants.POKE_SPECIE_MOVE_TABLE
import com.pokemanager.utils.Constants.POKE_SPECIE_TYPE_TABLE
import com.pokemanager.utils.Constants.POKE_TYPE_ID

@Entity(tableName = POKE_SPECIE_TYPE_TABLE)
data class PokeSpecieTypeCrossRef(
    @PrimaryKey(autoGenerate = true) val slot: Int = 0,
    val pokeSpecieId: Int = 0,
    var pokeTypeId: Int = 0,
)

data class PokeSpecieItemWithTypes(
    @Embedded val pokeSpecie: PokeSpecieItemEntity,
    @Relation(
        parentColumn = POKE_SPECIE_ID,
        entityColumn = POKE_SPECIE_ID,
    )
    val pokeTypeCross: MutableList<PokeSpecieTypeCrossRef>
)

@Entity(tableName = POKE_SPECIE_ABILITY_TABLE, primaryKeys = [POKE_SPECIE_ID, POKE_ABILITY_ID])
data class PokeSpecieAbilityCrossRef(
    val pokeSpecieId: Int = 0,
    var pokeAbilityId: Int = 0,
    val isHidden: Boolean = false
)

@Entity(tableName = POKE_SPECIE_MOVE_TABLE, primaryKeys = [POKE_SPECIE_ID, POKE_MOVE_ID])
data class PokeSpecieMoveCrossRef(
    val pokeSpecieId: Int = 0,
    var pokeMoveId: Int = 0
)

data class PokeSpecieDetailCompleteEntities(
    @Embedded val pokeSpecie: PokeSpecieDetailEntity,
    @Relation(
        parentColumn = POKE_SPECIE_ID,
        entityColumn = POKE_TYPE_ID,
        associateBy = Junction(PokeSpecieTypeCrossRef::class)
    )
    val pokeTypes: MutableList<PokeTypeEntity>,
    @Relation(
        parentColumn = POKE_SPECIE_ID,
        entityColumn = POKE_ABILITY_ID,
        associateBy = Junction(PokeSpecieAbilityCrossRef::class)
    )
    val pokeAbilities: MutableList<PokeAbilityEntity>,
    @Relation(
        parentColumn = POKE_SPECIE_ID,
        entityColumn = POKE_MOVE_ID,
        associateBy = Junction(PokeSpecieMoveCrossRef::class)
    )
    val pokeMoves: MutableList<PokeMoveEntity>,
    @Relation(
        parentColumn = POKE_SPECIE_EVOLUTION_CHAIN_ID,
        entityColumn = EVOLUTION_CHAIN_ID
    )
    val evolutionChainMembers: MutableList<EvolutionChainMemberEntity>
)
