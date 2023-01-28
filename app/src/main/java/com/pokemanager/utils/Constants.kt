package com.pokemanager.utils

object Constants {

    const val baseUrl = "https://pokeapi.co/api/v2/"

    const val DB_NAME = "PokeManager.db"

    const val POKE_SPECIE_TABLE = "pokeSpecies"
    const val POKE_SPECIE_ID = "pokeSpecieId"
    const val POKE_SPECIE_REMOTE_KEYS_TABLE = "poke_specie_remote_keys"

    const val POKE_TYPE_TABLE = "pokeTypes"
    const val POKE_TYPE_ID = "pokeTypeId"

    const val LAST_VALID_POKEMON_NUMBER = 905

    const val POKEMON_PAGING_STARTING_KEY = 0
    const val POKEMON_PAGING_PAGE_SIZE = 60
    const val POKEMON_PAGING_PREFETCH_DISTANCE = 30
    const val POKEMON_PAGING_MAX_SIZE = 150//POKEMON_PAGING_PAGE_SIZE + POKEMON_PAGING_PREFETCH_DISTANCE * 2 at minimum
}