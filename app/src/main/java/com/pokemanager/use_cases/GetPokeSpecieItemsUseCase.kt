package com.pokemanager.use_cases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pokemanager.data.DataAccessMode
import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.data.remote.PageKeyedPokemonItemsPagingSource
import com.pokemanager.data.remote.PokeManagerApi
import com.pokemanager.utils.Constants.POKEMON_PAGING_MAX_SIZE
import com.pokemanager.utils.Constants.POKEMON_PAGING_PAGE_SIZE
import com.pokemanager.utils.Constants.POKEMON_PAGING_PREFETCH_DISTANCE
import kotlinx.coroutines.flow.Flow

class GetPokeSpecieItemsUseCase(
    private val pokeManagerApi: PokeManagerApi
) {

    operator fun invoke(
        dataAccessMode: DataAccessMode
    ): Flow<PagingData<PokeSpecieItemDomain>> {
        when (dataAccessMode) {
            is DataAccessMode.DownloadAll -> {
                //access data from database
            }
            is DataAccessMode.RequestAndDownload -> {
                //if has the data from the db fetch it from there
                //else access the data from the query and persisted on the db
            }
            is DataAccessMode.OnlyRequest -> {
                //always access all from the api
            }
        }
        //is DataAccessMode.OnlyRequest
        return Pager(
            PagingConfig(
                pageSize = POKEMON_PAGING_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = POKEMON_PAGING_PREFETCH_DISTANCE,
                maxSize = POKEMON_PAGING_MAX_SIZE
            )
        ) {
            PageKeyedPokemonItemsPagingSource(
                pokeManagerApi
            )
        }.flow
    }

}