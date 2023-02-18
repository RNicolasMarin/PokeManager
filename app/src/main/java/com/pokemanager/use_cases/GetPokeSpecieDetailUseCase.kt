package com.pokemanager.use_cases

import com.pokemanager.data.DataAccessMode
import com.pokemanager.data.DataAccessMode.*
import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.data.mappers.toPokeSpecieItemDomain
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
    ): Flow<DataState<PokeSpecieItemDomain>> = flow {
        emit(DataState.Loading)

        when (dataAccessMode) {
            is DownloadAll -> {
                delay(500)
                val result = mainRepository.getPokeSpeciesDetailWithTypes(pokeSpecieId)?.toPokeSpecieItemDomain()
                    ?: return@flow
                emit(DataState.Success(result))
            }
            is RequestAndDownload -> {
                delay(500)
                var result = mainRepository.getPokeSpeciesDetailWithTypes(pokeSpecieId)?.toPokeSpecieItemDomain()
                if (result == null) {
                    mainRepository.requestAndPersistPokeSpecies(1, pokeSpecieId, false)
                    result = mainRepository.getPokeSpeciesDetailWithTypes(pokeSpecieId)?.toPokeSpecieItemDomain()
                        ?: return@flow
                    emit(DataState.Success(result))
                } else {
                    emit(DataState.Success(result))
                }
            }
            is OnlyRequest -> {
                val result = mainRepository.getPokeSpeciesDetailFromNetwork(pokeSpecieId)
                emit(DataState.Success(result))
            }
        }
    }
}