package com.pokemanager.ui.setUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.pokemanager.data.DataAccessMode
import com.pokemanager.data.preferences.PokeManagerPreferences
import com.pokemanager.ui.setUp.ChooseNameLanguagesFragment.*
import com.pokemanager.ui.setUp.ChooseNameLanguagesFragment.NameLanguagesToListOptions.*
import com.pokemanager.utils.NameLanguagesToList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetUpViewModel @Inject constructor(
    private val pokeManagerPreferences: PokeManagerPreferences
) : ViewModel() {

    private val _canContinue = MutableLiveData(false)
    val canContinue = _canContinue.asFlow()

    var languages = 0

    fun selectMode(mode: DataAccessMode) {
        dataAccessMode = mode
        _canContinue.postValue(true)
    }

    fun selectNameLanguage(nowChecked: Boolean, language: NameLanguagesToListOptions) {
        if (nowChecked) {
            languages++
        } else {
            languages--
        }
        _canContinue.postValue(languages != 0)

        with(nameLanguagesToList) {
            when(language) {
                ENGLISH -> showEnglishName = nowChecked
                JAP_HR_KT -> showJapHrKtName = nowChecked
                JAP_ROOMAJI -> showJapRoomajiName = nowChecked
            }
        }
    }

    fun continueFromChooseDataAccessMode() {
        dataAccessMode?.let { pokeManagerPreferences.saveDataAccessMode(it) }
    }

    fun continueFromChooseNameLanguages() {
        if (languages <= 0) return
        pokeManagerPreferences.saveNameLanguagesToList(nameLanguagesToList)
    }

    companion object {
        var dataAccessMode: DataAccessMode? = null
        var nameLanguagesToList = NameLanguagesToList()
    }

    fun getDataAccessMode() = dataAccessMode


}