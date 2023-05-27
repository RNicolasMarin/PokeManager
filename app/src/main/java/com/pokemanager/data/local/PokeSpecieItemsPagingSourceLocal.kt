package com.pokemanager.data.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.data.mappers.fromPokeSpecieItemWithTypesListToPokeSpecieItemDomainList
import com.pokemanager.data.repositories.MainRepository
import com.pokemanager.utils.Constants
import com.pokemanager.utils.KeyUtils.getNextKey
import com.pokemanager.utils.KeyUtils.getPrevKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class PokeSpecieItemsPagingSourceLocal(
    private val mainRepository: MainRepository
) : PagingSource<Int, PokeSpecieItemDomain>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokeSpecieItemDomain> = withContext(Dispatchers.IO) {
        return@withContext try {
            delay(500)
            val offset = params.key ?: Constants.POKEMON_PAGING_STARTING_KEY
            val itemsFromList = mainRepository.getPokeSpecieItemsWithTypes(
                limit = params.loadSize,
                offset = offset
            )

            val pokeSpecies = itemsFromList.fromPokeSpecieItemWithTypesListToPokeSpecieItemDomainList()
            val lastValidId = mainRepository.getPokeSpeciesLastId() ?: 0

            val prevKey = getPrevKey(offset, params.loadSize)
            val nextKey = getNextKey(pokeSpecies.toMutableList(), lastValidId)

            LoadResult.Page(
                data = pokeSpecies,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokeSpecieItemDomain>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.id
        }
    }
}