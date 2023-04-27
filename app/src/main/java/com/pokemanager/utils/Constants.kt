package com.pokemanager.utils

object Constants {

    const val baseUrl = "https://pokeapi.co/api/v2/"

    //SharedPreferences
    const val SHARED_PREFERENCES_NAME = "PokeManagerPreferences"
    const val SHARED_PREFERENCES_DATA_ACCESS_MODE = "SHARED_PREFERENCES_DATA_ACCESS_MODE"
    const val SHARED_PREFERENCES_DOWNLOAD_ALL_PROGRESS = "SHARED_PREFERENCES_DOWNLOAD_ALL_PROGRESS"
    const val SHARED_PREFERENCES_NAME_LANGUAGE_TO_LIST = "SHARED_PREFERENCES_NAME_LANGUAGE_TO_LIST"

    //DB
    const val DB_NAME = "PokeManager.db"

    const val POKE_SPECIE_TABLE = "pokeSpecies"
    const val POKE_SPECIE_ID = "pokeSpecieId"
    const val POKE_SPECIE_REMOTE_KEYS_TABLE = "poke_specie_remote_keys"

    //POKE_TYPE
    const val POKE_TYPE_TABLE = "pokeTypes"
    const val POKE_TYPE_ID = "pokeTypeId"

    const val POKE_SPECIE_TYPE_TABLE = "pokeSpecieTypes"

    //POKE_ABILITY
    const val POKE_ABILITY_TABLE = "pokeAbilities"
    const val POKE_ABILITY_ID = "pokeAbilityId"

    const val POKE_SPECIE_ABILITY_TABLE = "pokeSpecieAbilities"

    //POKE_MOVE
    const val POKE_MOVE_TABLE = "pokeMoves"
    const val POKE_MOVE_ID = "pokeMoveId"

    const val POKE_SPECIE_MOVE_TABLE = "pokeSpecieMoves"

    //POKE_EVOLUTION_CHAIN_MEMBER
    const val EVOLUTION_CHAIN_MEMBER_TABLE = "evolutionChainMembers"
    const val EVOLUTION_CHAIN_ID = "evolutionChainId"
    const val POKE_SPECIE_EVOLUTION_CHAIN_ID = "evolutionChainId"


    const val LAST_VALID_POKEMON_NUMBER = 905

    //Paging
    const val POKEMON_PAGING_STARTING_KEY = 0
    const val POKEMON_PAGING_PAGE_SIZE = 60
    const val POKEMON_PAGING_PREFETCH_DISTANCE = 30
    const val POKEMON_PAGING_MAX_SIZE = 150//POKEMON_PAGING_PAGE_SIZE + POKEMON_PAGING_PREFETCH_DISTANCE * 2 at minimum

    //Service and Notification
    const val SERVICE_ACTION_START = "SERVICE_ACTION_START"
    const val NOTIFICATION_CHANNEL_ID = "pokeManager_channel"
    const val NOTIFICATION_CHANNEL_NAME = "PokeManager"
    const val NOTIFICATION_ID = 1

    const val STAT_HP = "hp"
    const val STAT_SPEED = "speed"
    const val STAT_ATTACK = "attack"
    const val STAT_DEFENSE = "defense"
    const val STAT_SP_ATTACK = "special-attack"
    const val STAT_SP_DEFENSE = "special-defense"
}