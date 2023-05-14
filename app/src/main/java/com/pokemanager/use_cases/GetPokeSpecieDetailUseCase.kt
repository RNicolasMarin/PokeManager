package com.pokemanager.use_cases

import com.pokemanager.data.DataAccessMode
import com.pokemanager.data.DataAccessMode.*
import com.pokemanager.data.domain.PokeSpecieDetailDomain
import com.pokemanager.data.mappers.fromPokeSpecieDetailCompleteEntitiesListToPokeSpecieDetailDomainList
import com.pokemanager.data.repositories.MainRepository
import com.pokemanager.utils.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPokeSpecieDetailUseCase(
    private val mainRepository: MainRepository,
) {

    operator fun invoke(
        pokeSpecieId: Int,
        dataAccessMode: DataAccessMode
    ): Flow<DataState<List<PokeSpecieDetailDomain>>> = flow {
        emit(DataState.Loading)

        when (dataAccessMode) {
            is DownloadAll -> {
                delay(500)
                val result = mainRepository.getPokeSpeciesDetailCompleteEntities(pokeSpecieId).fromPokeSpecieDetailCompleteEntitiesListToPokeSpecieDetailDomainList()
                emit(DataState.Success(result))
            }
            is RequestAndDownload -> {
                delay(500)
                var result = mainRepository.getPokeSpeciesDetailCompleteEntities(pokeSpecieId).fromPokeSpecieDetailCompleteEntitiesListToPokeSpecieDetailDomainList()
                if (result.isEmpty() || result[0].areDetailsEmpty()) {
                    mainRepository.requestAndPersistPokeSpecies(1, pokeSpecieId - 1, clearBefore = false, onlyItemData = false)
                    result = mainRepository.getPokeSpeciesDetailCompleteEntities(pokeSpecieId).fromPokeSpecieDetailCompleteEntitiesListToPokeSpecieDetailDomainList()
                    emit(DataState.Success(result))
                } else {
                    emit(DataState.Success(result))
                }
            }
            is OnlyRequest -> {
                val result = mainRepository.getPokeSpeciesDetailFromNetwork(pokeSpecieId)
                if (result == null) {
                    delay(500)
                    emit(DataState.Error)
                } else {
                    emit(DataState.Success(result))
                }
            }
        }
    }
}