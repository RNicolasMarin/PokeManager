package com.pokemanager.ui.species

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokemanager.data.domain.PokeSpecieDetailDomain
import com.pokemanager.data.preferences.PokeManagerPreferences
import com.pokemanager.use_cases.GetPokeSpecieDetailUseCase
import com.pokemanager.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPokeSpecieViewModel @Inject constructor(
    private val getPokeSpecieDetailUseCase: GetPokeSpecieDetailUseCase,
    private val pokeManagerPreferences: PokeManagerPreferences
): ViewModel() {

    private val _pokeSpecieDetail = MutableStateFlow<DataState<PokeSpecieDetailDomain>>(DataState.Loading)
    val pokeSpecieDetail = _pokeSpecieDetail.asStateFlow()

    fun loadPokeSpecieData(pokeSpecieId: Int) {
        val mode = pokeManagerPreferences.getDataAccessModeNonNull()
        viewModelScope.launch {
            getPokeSpecieDetailUseCase(pokeSpecieId, mode).collectLatest {
                _pokeSpecieDetail.emit(it)
            }
        }
    }
}