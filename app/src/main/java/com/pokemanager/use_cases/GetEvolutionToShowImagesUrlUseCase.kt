package com.pokemanager.use_cases

import com.pokemanager.data.DataAccessMode
import com.pokemanager.data.DataAccessMode.*
import com.pokemanager.data.repositories.MainRepository
import com.pokemanager.ui.species.detail.tabs.EvolutionRow.*
import com.pokemanager.ui.species.detail.tabs.EvolutionToShow
import com.pokemanager.utils.DataState
import com.pokemanager.utils.UrlUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetEvolutionToShowImagesUrlUseCase(
    private val mainRepository: MainRepository,
) {

    operator fun invoke(
        evolutionToShow: EvolutionToShow,
        dataAccessMode: DataAccessMode
    ): Flow<DataState<EvolutionToShow>> = flow {
        emit(DataState.Loading)

        for (row in evolutionToShow.evolutionRows) {
            when (row) {
                is EvolutionRowCenter -> {
                    if (row.rowSpecieCenter.imageUrl.isBlank()) {
                        row.rowSpecieCenter.imageUrl = getImageUrlForSpecie(dataAccessMode, row.rowSpecieCenter.id)
                    }
                }
                is EvolutionRowBothSides -> {
                    if (row.rowSpecieLeft.imageUrl.isBlank()) {
                        row.rowSpecieLeft.imageUrl = getImageUrlForSpecie(dataAccessMode, row.rowSpecieLeft.id)
                    }
                    if (row.rowSpecieRight.imageUrl.isBlank()) {
                        row.rowSpecieRight.imageUrl = getImageUrlForSpecie(dataAccessMode, row.rowSpecieRight.id)
                    }
                }
                is EvolutionRowLeftSide -> {
                    if (row.rowSpecieLeft.imageUrl.isBlank()) {
                        row.rowSpecieLeft.imageUrl = getImageUrlForSpecie(dataAccessMode, row.rowSpecieLeft.id)
                    }
                }
                is EvolutionRowRightSide -> {
                    if (row.rowSpecieRight.imageUrl.isBlank()) {
                        row.rowSpecieRight.imageUrl = getImageUrlForSpecie(dataAccessMode, row.rowSpecieRight.id)
                    }
                }
            }
        }
        delay(500)
        emit(DataState.Success(evolutionToShow))
    }

    private suspend fun getImageUrlForSpecie(dataAccessMode: DataAccessMode, id: Int): String {
        when (dataAccessMode) {
            is DownloadAll -> {
                delay(500)
                return mainRepository.getImageUrlForSpecieLocal(id)
            }
            is RequestAndDownload -> {
                delay(500)
                var result = mainRepository.getImageUrlForSpecieLocal(id)
                return result.ifEmpty {
                    mainRepository.requestAndPersistPokeSpecies(
                        1,
                        id - 1,
                        clearBefore = false,
                        onlyItemData = false
                    )
                    result = mainRepository.getImageUrlForSpecieLocal(id)
                    result
                }
            }
            is OnlyRequest -> {
                return UrlUtils.getImageUrl(mainRepository.getImageUrlForSpecieNetwork(id).sprites)
            }
        }

    }
}