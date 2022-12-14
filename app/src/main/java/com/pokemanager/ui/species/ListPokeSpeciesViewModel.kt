package com.pokemanager.ui.species

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokemanager.data.DataAccessMode
import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.use_cases.GetPokeSpecieItemsUseCase
import com.pokemanager.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListPokeSpeciesViewModel @Inject constructor(
    getPokeSpecieItemsUseCase: GetPokeSpecieItemsUseCase
): ViewModel() {

    val pokeSpecies = mutableListOf<DataState<List<PokeSpecieItemDomain>>>()

    init {
        getPokeSpecieItemsUseCase(DataAccessMode.OnlyRequest)
            .onEach {  }
            .launchIn(viewModelScope)
    }
}