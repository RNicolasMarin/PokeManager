package com.pokemanager.utils

object Constants {

    const val baseUrl = "https://pokeapi.co/api/v2/"

    const val LAST_VALID_POKEMON_NUMBER = 905

    const val POKEMON_PAGING_STARTING_KEY = 0
    const val POKEMON_PAGING_PAGE_SIZE = 60
    const val POKEMON_PAGING_PREFETCH_DISTANCE = 30
    const val POKEMON_PAGING_MAX_SIZE = 150//POKEMON_PAGING_PAGE_SIZE + POKEMON_PAGING_PREFETCH_DISTANCE * 2 at minimum
}