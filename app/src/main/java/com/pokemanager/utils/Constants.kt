package com.pokemanager.utils

import com.pokemanager.data.DataAccessMode

object Constants {

    val MODE = DataAccessMode.DownloadAll

    const val baseUrl = "https://pokeapi.co/api/v2/"

    const val SHARED_PREFERENCES_NAME = "PokeManagerPreferences"
    const val SHARED_PREFERENCES_DATA_ACCESS_MODE = "PokeManagerPreferences"

    const val DB_NAME = "PokeManager.db"

    const val POKE_SPECIE_TABLE = "pokeSpecies"
    const val POKE_SPECIE_ID = "pokeSpecieId"
    const val POKE_SPECIE_REMOTE_KEYS_TABLE = "poke_specie_remote_keys"

    const val POKE_TYPE_TABLE = "pokeTypes"
    const val POKE_TYPE_ID = "pokeTypeId"

    const val POKE_SPECIE_TYPE_TABLE = "pokeSpecieTypes"

    const val LAST_VALID_POKEMON_NUMBER = 905

    const val POKEMON_PAGING_STARTING_KEY = 0
    const val POKEMON_PAGING_PAGE_SIZE = 60
    const val POKEMON_PAGING_PREFETCH_DISTANCE = 30
    const val POKEMON_PAGING_MAX_SIZE = 150//POKEMON_PAGING_PAGE_SIZE + POKEMON_PAGING_PREFETCH_DISTANCE * 2 at minimum

    const val SERVICE_ACTION_START = "SERVICE_ACTION_START"
    const val NOTIFICATION_CHANNEL_ID = "pokeManager_channel"
    const val NOTIFICATION_CHANNEL_NAME = "PokeManager"
    const val NOTIFICATION_ID = 1
    const val ACTION_SHOW_DOWNLOADING_FRAGMENT = "ACTION_SHOW_DOWNLOADING_FRAGMENT"
}