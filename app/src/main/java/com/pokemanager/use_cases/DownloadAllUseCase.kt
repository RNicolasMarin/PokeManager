package com.pokemanager.use_cases

import com.pokemanager.data.PokeSpecieItemsRemoteMediator
import com.pokemanager.data.local.PokeManagerDatabase
import com.pokemanager.data.remote.PokeManagerApi
import com.pokemanager.utils.Constants.LAST_VALID_POKEMON_NUMBER
import com.pokemanager.utils.Constants.POKEMON_PAGING_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DownloadAllUseCase(
    private val pokeManagerApi: PokeManagerApi,
    private val pokeDatabase: PokeManagerDatabase
) {

    operator fun invoke(): Flow<DownloadAllProgress> = flow {
        emit(DownloadAllProgress(0))

        var count = 1
        for (i in 0..LAST_VALID_POKEMON_NUMBER step POKEMON_PAGING_PAGE_SIZE) {
            PokeSpecieItemsRemoteMediator.requestAndPersistPokeSpecies(pokeManagerApi, pokeDatabase, POKEMON_PAGING_PAGE_SIZE, i, i == 0)
            emit(DownloadAllProgress(count))
            count++
        }
        count--
        emit(DownloadAllProgress(count, true))
    }

    data class DownloadAllProgress(
        val step: Int = 0,
        val isFinished: Boolean = false
    )
}