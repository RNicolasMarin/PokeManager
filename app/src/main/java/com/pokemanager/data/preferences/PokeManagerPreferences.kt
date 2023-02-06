package com.pokemanager.data.preferences

import com.pokemanager.data.DataAccessMode

interface PokeManagerPreferences {
    fun saveDataAccessMode(dataAccessMode: DataAccessMode)
}