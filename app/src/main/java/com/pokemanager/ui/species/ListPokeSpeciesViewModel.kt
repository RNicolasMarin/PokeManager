package com.pokemanager.ui.species

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pokemanager.data.DataAccessMode
import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.use_cases.GetPokeSpecieItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ListPokeSpeciesViewModel @Inject constructor(
    getPokeSpecieItemsUseCase: GetPokeSpecieItemsUseCase
): ViewModel() {

    val pokeSpecies: Flow<PagingData<PokeSpecieItemDomain>>
            = getPokeSpecieItemsUseCase(DataAccessMode.OnlyRequest)
        // cachedIn allows paging to remain active in the viewModel scope, so even if the UI
        // showing the paged data goes through lifecycle changes, pagination remains cached and
        // the UI does not have to start paging from the beginning when it resumes.
            .cachedIn(viewModelScope)
}