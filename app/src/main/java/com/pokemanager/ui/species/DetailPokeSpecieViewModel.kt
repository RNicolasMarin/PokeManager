package com.pokemanager.ui.species

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokemanager.data.domain.PokeSpecieDetailDomain
import com.pokemanager.data.preferences.PokeManagerPreferences
import com.pokemanager.use_cases.GetPokeSpecieDetailUseCase
import com.pokemanager.utils.Constants.LAST_VALID_POKEMON_NUMBER
import com.pokemanager.utils.DataState
import com.pokemanager.utils.DataState.*
import com.pokemanager.utils.VisualUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPokeSpecieViewModel @Inject constructor(
    private val getPokeSpecieDetailUseCase: GetPokeSpecieDetailUseCase,
    private val pokeManagerPreferences: PokeManagerPreferences
): ViewModel() {

    private var pokeSpecieDetailAllForms = mutableListOf<PokeSpecieDetailDomain>()
    private var currentFormPosition = 0

    private val _pokeSpecieDetail = MutableStateFlow<DataState<PokeSpecieDetailDomain>>(Loading)
    val pokeSpecieDetail = _pokeSpecieDetail.asStateFlow()

    fun loadPokeSpecieData(pokeSpecieId: Int) {
        currentFormPosition = 0
        val mode = pokeManagerPreferences.getDataAccessModeNonNull()
        viewModelScope.launch {
            getPokeSpecieDetailUseCase(pokeSpecieId, mode).collectLatest {
                if (it is Success) {
                    pokeSpecieDetailAllForms = it.data as MutableList<PokeSpecieDetailDomain>
                    _pokeSpecieDetail.emit(Success(pokeSpecieDetailAllForms[currentFormPosition]))
                }
            }
        }
    }

    fun loadPreviousPokeSpecieData() {
        if (_pokeSpecieDetail.value is Success && pokeSpecieDetailAllForms.isNotEmpty()) {
            var currentId = pokeSpecieDetailAllForms[0].id
            if (currentId > 1) {
               currentId--
               loadPokeSpecieData(currentId)
            }
        }
    }

    fun loadNextPokeSpecieData() {
        if (_pokeSpecieDetail.value is Success && pokeSpecieDetailAllForms.isNotEmpty()) {
            var currentId = pokeSpecieDetailAllForms[0].id
            if (currentId < LAST_VALID_POKEMON_NUMBER) {
                currentId++
                loadPokeSpecieData(currentId)
            }
        }
    }

    fun changeForm() {
        if (pokeSpecieDetailAllForms.isNotEmpty()) {
            if (currentFormPosition < pokeSpecieDetailAllForms.size -1) {
                currentFormPosition++
            } else {
                currentFormPosition = 0
            }
            viewModelScope.launch {
                _pokeSpecieDetail.emit(Success(pokeSpecieDetailAllForms[currentFormPosition]))
            }
        }
    }

    fun isThereMultipleForms(): Boolean {
        return pokeSpecieDetail.value is Success && pokeSpecieDetailAllForms.size > 1
    }

    fun getNamesByLanguage() : NamesToShow {
        return VisualUtils.getNamesByLanguage(
            pokeSpecieDetailAllForms[currentFormPosition],
            pokeManagerPreferences.getNameLanguagesToListNonNull()
        )
    }
}