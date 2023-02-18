package com.pokemanager.use_cases

import androidx.paging.*
import com.pokemanager.data.DataAccessMode
import com.pokemanager.data.DataAccessMode.*
import com.pokemanager.data.PokeSpecieItemsRemoteMediator
import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.data.local.PokeSpecieItemsPagingSourceLocal
import com.pokemanager.data.mappers.toPokeSpecieItemDomain
import com.pokemanager.data.remote.PokeSpecieItemsPagingSourceRemote
import com.pokemanager.data.repositories.MainRepository
import com.pokemanager.utils.Constants.POKEMON_PAGING_MAX_SIZE
import com.pokemanager.utils.Constants.POKEMON_PAGING_PAGE_SIZE
import com.pokemanager.utils.Constants.POKEMON_PAGING_PREFETCH_DISTANCE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPokeSpecieItemsUseCase(
    private val mainRepository: MainRepository
) {

    operator fun invoke(
        dataAccessMode: DataAccessMode
    ): Flow<PagingData<PokeSpecieItemDomain>> {
        val config = PagingConfig(
            pageSize = POKEMON_PAGING_PAGE_SIZE,
            prefetchDistance = POKEMON_PAGING_PREFETCH_DISTANCE,
            maxSize = POKEMON_PAGING_MAX_SIZE,
            enablePlaceholders = getEnablePlaceHolders(dataAccessMode),
            /*maxSize = PagingConfig.MAX_SIZE_UNBOUNDED
            jumpThreshold = Int.MIN_VALUE*/
        )
        return when (dataAccessMode) {
            is DownloadAll -> {
                //access data from database
                Pager(config) {
                    PokeSpecieItemsPagingSourceLocal(mainRepository)
                }.flow
            }
            is RequestAndDownload -> {
                //if has the data from the db fetch it from there
                //else access the data from the query and persisted on the db
                val pagingSourceFactory = { mainRepository.getPokeSpeciesWithTypes() }

                @OptIn(ExperimentalPagingApi::class)
                Pager(
                    config = config,
                    remoteMediator = PokeSpecieItemsRemoteMediator(mainRepository),
                    pagingSourceFactory = pagingSourceFactory
                ).flow.map { pagingData ->
                    pagingData.map {
                        it.toPokeSpecieItemDomain()
                    }
                }
            }
            is OnlyRequest -> {
                //always access all from the api
                Pager(config) {
                    PokeSpecieItemsPagingSourceRemote(mainRepository)
                }.flow
            }
        }
    }

    private fun getEnablePlaceHolders(dataAccessMode: DataAccessMode): Boolean {
        return when(dataAccessMode) {
            is DownloadAll -> true //works no matter the value

            is RequestAndDownload -> true
            //true, if I wait until finish loading when loads the first pages (there's 3 pages loads when first starts) it works,
            //but if I move before finish loading it will load the items from the wrong page

            is OnlyRequest -> false
        }
    }
}