package com.pokemanager.data.preferences

import android.content.SharedPreferences
import com.pokemanager.data.DataAccessMode
import com.pokemanager.data.DataAccessMode.RequestAndDownload.toDataAccessMode
import com.pokemanager.utils.Constants.SHARED_PREFERENCES_DATA_ACCESS_MODE

class PokeManagerPreferencesImpl(
    private val sharedPref: SharedPreferences
) : PokeManagerPreferences {

    override fun saveDataAccessMode(dataAccessMode: DataAccessMode) {
        val value = dataAccessMode.toString()
        sharedPref.edit().putString(SHARED_PREFERENCES_DATA_ACCESS_MODE, value).apply()
    }

    override fun getDataAccessMode(): DataAccessMode {
        val value = sharedPref.getString(SHARED_PREFERENCES_DATA_ACCESS_MODE, "") ?: ""
        return value.toDataAccessMode()
    }
}