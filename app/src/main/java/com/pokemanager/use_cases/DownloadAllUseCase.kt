package com.pokemanager.use_cases

import com.pokemanager.data.preferences.PokeManagerPreferences
import com.pokemanager.data.repositories.MainRepository
import com.pokemanager.utils.Constants.LAST_VALID_POKEMON_NUMBER
import com.pokemanager.utils.Constants.POKEMON_PAGING_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class DownloadAllUseCase(
    private val mainRepository: MainRepository,
    private val pokeManagerPreferences: PokeManagerPreferences
) {

    operator fun invoke(): Flow<DownloadAllProgress> = flow {
        emitAll(emitAndSave(DownloadAllProgress(0)))

        var count = 1
        for (i in 0..LAST_VALID_POKEMON_NUMBER step POKEMON_PAGING_PAGE_SIZE) {
            mainRepository.requestAndPersistPokeSpecies(POKEMON_PAGING_PAGE_SIZE, i, i == 0)
            emitAll(emitAndSave(DownloadAllProgress(count)))
            count++
        }
        count--
        emitAll(emitAndSave(DownloadAllProgress(count, true)))
    }

    private suspend fun emitAndSave(progress: DownloadAllProgress) = flow {
        pokeManagerPreferences.saveDownloadAllProgress(progress)
        emit(progress)
    }

    data class DownloadAllProgress(
        val step: Int = 0,
        val isFinished: Boolean = false
    )
}