package com.pokemanager.ui.setUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.pokemanager.data.DataAccessMode
import com.pokemanager.data.preferences.PokeManagerPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetUpViewModel @Inject constructor(
    private val pokeManagerPreferences: PokeManagerPreferences
) : ViewModel() {

    private val _canContinue = MutableLiveData(false)
    val canContinue = _canContinue.asFlow()

    fun selectMode(mode: DataAccessMode) {
        dataAccessMode = mode
        _canContinue.postValue(true)
    }

    fun continueFromChooseHandleDataMode() {
        dataAccessMode?.let { pokeManagerPreferences.saveDataAccessMode(it) }
    }

    companion object {
        var dataAccessMode: DataAccessMode? = null
    }

    fun getDataAccessMode() = dataAccessMode


}