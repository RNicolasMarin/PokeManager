package com.pokemanager.data.preferences

import com.pokemanager.data.DataAccessMode
import com.pokemanager.use_cases.DownloadAllUseCase.*
import com.pokemanager.utils.NameLanguagesToList

interface PokeManagerPreferences {
    fun saveDataAccessMode(dataAccessMode: DataAccessMode)

    fun getDataAccessModeNonNull() : DataAccessMode

    fun getDataAccessMode() : DataAccessMode?

    fun saveDownloadAllProgress(progress: DownloadAllProgress)

    fun getDownloadAllProgress() : DownloadAllProgress?

    fun saveNameLanguagesToList(nameLanguagesToList: NameLanguagesToList)

    fun getNameLanguagesToListNonNull() : NameLanguagesToList

    fun getNameLanguagesToList() : NameLanguagesToList?
}