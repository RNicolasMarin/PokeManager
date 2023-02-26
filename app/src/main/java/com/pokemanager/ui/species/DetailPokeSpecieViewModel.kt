package com.pokemanager.ui.species

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokemanager.data.domain.PokeSpecieDetailDomain
import com.pokemanager.data.preferences.PokeManagerPreferences
import com.pokemanager.use_cases.GetPokeSpecieDetailUseCase
import com.pokemanager.utils.Constants.LAST_VALID_POKEMON_NUMBER
import com.pokemanager.utils.DataState
import com.pokemanager.utils.DataState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPokeSpecieViewModel @Inject constructor(
    private val getPokeSpecieDetailUseCase: GetPokeSpecieDetailUseCase,
    private val pokeManagerPreferences: PokeManagerPreferences
): ViewModel() {

    private val _pokeSpecieDetail = MutableStateFlow<DataState<PokeSpecieDetailDomain>>(Loading)
    val pokeSpecieDetail = _pokeSpecieDetail.asStateFlow()

    fun loadPokeSpecieData(pokeSpecieId: Int) {
        val mode = pokeManagerPreferences.getDataAccessModeNonNull()
        viewModelScope.launch {
            getPokeSpecieDetailUseCase(pokeSpecieId, mode).collectLatest {
                _pokeSpecieDetail.emit(it)
            }
        }
    }

    fun loadPreviousPokeSpecieData() {
        if (_pokeSpecieDetail.value is Success) {
            var currentId = (_pokeSpecieDetail.value as Success<PokeSpecieDetailDomain>).data.id
            if (currentId > 1) {
               currentId--
               loadPokeSpecieData(currentId)
            }
        }
    }

    fun loadNextPokeSpecieData() {
        if (_pokeSpecieDetail.value is Success) {
            var currentId = (_pokeSpecieDetail.value as Success<PokeSpecieDetailDomain>).data.id
            if (currentId < LAST_VALID_POKEMON_NUMBER) {
                currentId++
                loadPokeSpecieData(currentId)
            }
        }
    }
}