package com.pokemanager.ui.species

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pokemanager.data.DataAccessMode
import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.data.preferences.PokeManagerPreferences
import com.pokemanager.use_cases.GetPokeSpecieItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPokeSpeciesViewModel @Inject constructor(
    getPokeSpecieItemsUseCase: GetPokeSpecieItemsUseCase,
    private val pokeManagerPreferences: PokeManagerPreferences
): ViewModel() {

    val showFirstLoading = MutableLiveData(true)
    val mode = MutableLiveData<DataAccessMode>()
    val pokeSpecies: Flow<PagingData<PokeSpecieItemDomain>>
            = getPokeSpecieItemsUseCase(getMode())
        // cachedIn allows paging to remain active in the viewModel scope, so even if the UI
        // showing the paged data goes through lifecycle changes, pagination remains cached and
        // the UI does not have to start paging from the beginning when it resumes.
            .cachedIn(viewModelScope)

    private fun getMode(): DataAccessMode {
        val value = pokeManagerPreferences.getDataAccessModeNonNull()
        mode.postValue(value)
        return value
    }

    private val totalSeconds = 5
    var secondsLeftToShowList = totalSeconds
    var launchTimer = true
    fun tryToUpdateShowFirstLoading(loadingRequestAndDownload: Boolean, loadingOnlyRequest: Boolean) {
        if (mode.value !is DataAccessMode.RequestAndDownload) {
            showFirstLoading.postValue(loadingOnlyRequest)
            return
        }

        if (!loadingRequestAndDownload) return

        secondsLeftToShowList = totalSeconds

        if (!launchTimer) return
        launchTimer = false
        CoroutineScope(Dispatchers.IO).launch {
            while (secondsLeftToShowList > 0) {
                delay(1000)
                secondsLeftToShowList--
            }
            showFirstLoading.postValue(false)
        }
    }
}