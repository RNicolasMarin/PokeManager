package com.pokemanager.ui.species.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokemanager.data.DataAccessMode
import com.pokemanager.data.domain.PokeSpecieDetailDomain
import com.pokemanager.data.preferences.PokeManagerPreferences
import com.pokemanager.ui.species.detail.tabs.EvolutionToShow
import com.pokemanager.use_cases.GetEvolutionToShowImagesUrlUseCase
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
    private val getEvolutionToShowImagesUrlUseCase: GetEvolutionToShowImagesUrlUseCase,
    private val pokeManagerPreferences: PokeManagerPreferences
): ViewModel() {

    private var pokeSpecieDetailAllForms = mutableListOf<PokeSpecieDetailDomain>()
    private var currentFormPosition = 0

    private val _pokeSpecieDetail = MutableStateFlow<DataState<PokeSpecieDetailDomain>>(Loading)
    val pokeSpecieDetail = _pokeSpecieDetail.asStateFlow()

    private val _evolutionToShow = MutableStateFlow<DataState<EvolutionToShow>>(Loading)
    val evolutionToShow = _evolutionToShow.asStateFlow()

    private val _selectedTabOne = MutableStateFlow(true)
    private val _selectedTabTwo = MutableStateFlow(false)
    private val _selectedTabThree = MutableStateFlow(false)
    private val _selectedTabFour = MutableStateFlow(false)
    val selectedTabOne = _selectedTabOne.asStateFlow()
    val selectedTabTwo = _selectedTabTwo.asStateFlow()
    val selectedTabThree = _selectedTabThree.asStateFlow()
    val selectedTabFour = _selectedTabFour.asStateFlow()

    private var pagePosition = 0

    fun setPagePosition(position: Int) {
        pagePosition = position
        _selectedTabOne.value = position == 0
        _selectedTabTwo.value = position == 1
        _selectedTabThree.value = position == 2
        _selectedTabFour.value = position == 3
    }

    fun loadPokeSpecieData(pokeSpecieId: Int) {
        val currentValue = _pokeSpecieDetail.value
        if (currentValue is Success && currentValue.data.defaultFormId == pokeSpecieId) return

        currentFormPosition = 0
        val mode = pokeManagerPreferences.getDataAccessModeNonNull()
        viewModelScope.launch {
            getPokeSpecieDetailUseCase(pokeSpecieId, mode).collectLatest {
                if (it is Success) {
                    pokeSpecieDetailAllForms = it.data as MutableList<PokeSpecieDetailDomain>
                    val pokeSpecie = pokeSpecieDetailAllForms[currentFormPosition]
                    showPokeSpecie(pokeSpecie, mode)
                }
            }
        }
    }

    private suspend fun showPokeSpecie(pokeSpecie: PokeSpecieDetailDomain, mode: DataAccessMode) {
        _pokeSpecieDetail.emit(Success(pokeSpecie))

        updateEvolutionToShow(pokeSpecie, mode)
    }

    //check if keep it here or move to a use case after adding the viewpager
    private suspend fun updateEvolutionToShow(
        pokeSpecie: PokeSpecieDetailDomain,
        mode: DataAccessMode
    ) {
        //Later if the evolutionChaiId on the specie is the same as on the EvolutionToShow only update the selected and refresh, else load everything
        if (_evolutionToShow.value is Success) {
            var evolutionToShow = (_evolutionToShow.value as Success<EvolutionToShow>).data

            if (pokeSpecie.evolutionChain.chainId == evolutionToShow.chainId) {
                evolutionToShow = VisualUtils.updateSelectedEvolutionRowSpecie(evolutionToShow, pokeSpecie.defaultFormId)
                getEvolutionToShowImagesUrlUseCase(evolutionToShow, mode).collectLatest {
                    _evolutionToShow.emit(it)
                }
            } else {
                loadNewEvolutionToShow(pokeSpecie, mode)
            }
        } else {
            loadNewEvolutionToShow(pokeSpecie, mode)
        }
    }

    private suspend fun loadNewEvolutionToShow(
        pokeSpecie: PokeSpecieDetailDomain,
        mode: DataAccessMode
    ) {
        val evolutionToShow = VisualUtils.getEvolutionToShow(
            pokeSpecie.evolutionChain.chain,
            pokeSpecie.defaultFormId,
            pokeSpecie.evolutionChain.chainId,
            pokeSpecieDetailAllForms[0].imageUrl
        ) ?: return

        getEvolutionToShowImagesUrlUseCase(evolutionToShow, mode).collectLatest {
            _evolutionToShow.emit(it)
        }
    }

    fun loadPreviousPokeSpecieData() {
        loadPreviousOrNextPokeSpecieData(false)
    }

    fun loadNextPokeSpecieData() {
        loadPreviousOrNextPokeSpecieData(true)
    }

    private fun loadPreviousOrNextPokeSpecieData(isNext: Boolean) {
        if (_pokeSpecieDetail.value is Success && pokeSpecieDetailAllForms.isNotEmpty()) {
            var currentId = pokeSpecieDetailAllForms[0].id

            val changeData = (isNext && currentId < LAST_VALID_POKEMON_NUMBER) || (!isNext && currentId > 1)
            currentId += (if (isNext) 1 else -1)

            if (changeData) {
                loadPokeSpecieData(currentId)
            }
        }
    }

    fun changeForm() {
        if (pokeSpecieDetailAllForms.isEmpty()) return

        currentFormPosition = if (currentFormPosition < pokeSpecieDetailAllForms.size -1)
                currentFormPosition + 1 else 0

        viewModelScope.launch {
            showPokeSpecie(pokeSpecieDetailAllForms[currentFormPosition], pokeManagerPreferences.getDataAccessModeNonNull())
        }
    }

    fun isThereMultipleForms() = pokeSpecieDetail.value is Success && pokeSpecieDetailAllForms.size > 1

    fun getNamesByLanguage() =
        VisualUtils.getNamesByLanguage(
            pokeSpecieDetailAllForms[currentFormPosition],
            pokeManagerPreferences.getNameLanguagesToListNonNull()
        )
}