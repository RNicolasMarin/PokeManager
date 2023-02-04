package com.pokemanager.data.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.data.mappers.fromPokeSpecieWithTypesListToPokeSpecieItemDomainList
import com.pokemanager.data.repositories.MainRepository
import com.pokemanager.utils.Constants
import com.pokemanager.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class PokeSpecieItemsPagingSourceLocal(
    private val mainRepository: MainRepository
) : PagingSource<Int, PokeSpecieItemDomain>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokeSpecieItemDomain> = withContext(Dispatchers.IO) {
        return@withContext try {

            val offset = params.key ?: Constants.POKEMON_PAGING_STARTING_KEY
            val itemsFromList = mainRepository.getPokeSpeciesWithTypes(
                limit = params.loadSize,
                offset = offset
            )

            val pokeSpecies = itemsFromList.fromPokeSpecieWithTypesListToPokeSpecieItemDomainList()
            val lastValidId = mainRepository.getPokeSpeciesLastId() ?: 0

            val prevKey = Utils.getPrevKey(offset, params.loadSize)
            val nextKey = Utils.getNextKeyD(pokeSpecies, lastValidId)

            LoadResult.Page(
                data = pokeSpecies,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokeSpecieItemDomain>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.id
        }
    }
}