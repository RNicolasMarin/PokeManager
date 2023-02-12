package com.pokemanager.data.preferences

import com.pokemanager.data.DataAccessMode
import com.pokemanager.use_cases.DownloadAllUseCase.*

interface PokeManagerPreferences {
    fun saveDataAccessMode(dataAccessMode: DataAccessMode)

    fun getDataAccessModeNonNull() : DataAccessMode

    fun getDataAccessMode() : DataAccessMode?

    fun saveDownloadAllProgress(progress: DownloadAllProgress)

    fun getDownloadAllProgress() : DownloadAllProgress?
}