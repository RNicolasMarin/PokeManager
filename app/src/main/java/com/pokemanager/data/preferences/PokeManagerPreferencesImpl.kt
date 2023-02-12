package com.pokemanager.data.preferences

import android.content.SharedPreferences
import com.google.gson.Gson
import com.pokemanager.data.DataAccessMode
import com.pokemanager.data.DataAccessMode.RequestAndDownload.toDataAccessMode
import com.pokemanager.use_cases.DownloadAllUseCase.*
import com.pokemanager.utils.Constants.SHARED_PREFERENCES_DATA_ACCESS_MODE
import com.pokemanager.utils.Constants.SHARED_PREFERENCES_DOWNLOAD_ALL_PROGRESS

class PokeManagerPreferencesImpl(
    private val sharedPref: SharedPreferences
) : PokeManagerPreferences {

    override fun saveDataAccessMode(dataAccessMode: DataAccessMode) {
        val value = dataAccessMode.toString()
        sharedPref.edit().putString(SHARED_PREFERENCES_DATA_ACCESS_MODE, value).apply()
    }

    override fun getDataAccessMode(): DataAccessMode? {
        val value = sharedPref.getString(SHARED_PREFERENCES_DATA_ACCESS_MODE, "") ?: ""
        if (value.isEmpty()) return null
        return value.toDataAccessMode()
    }

    override fun getDataAccessModeNonNull(): DataAccessMode {
        val value = sharedPref.getString(SHARED_PREFERENCES_DATA_ACCESS_MODE, "") ?: ""
        return value.toDataAccessMode()
    }

    override fun saveDownloadAllProgress(progress: DownloadAllProgress) {
        val value = Gson().toJson(progress)
        sharedPref.edit().putString(SHARED_PREFERENCES_DOWNLOAD_ALL_PROGRESS, value).apply()
    }

    override fun getDownloadAllProgress(): DownloadAllProgress? {
        val value = sharedPref.getString(SHARED_PREFERENCES_DOWNLOAD_ALL_PROGRESS, "") ?: ""
        if (value.isEmpty()) return null
        return Gson().fromJson(value, DownloadAllProgress::class.java)
    }
}