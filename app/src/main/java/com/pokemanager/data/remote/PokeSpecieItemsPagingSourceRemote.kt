package com.pokemanager.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.data.mappers.toPokeSpecieItemDomain
import com.pokemanager.data.repositories.MainRepository
import com.pokemanager.utils.Constants.LAST_VALID_POKEMON_NUMBER
import com.pokemanager.utils.Constants.POKEMON_PAGING_STARTING_KEY
import com.pokemanager.utils.KeyUtils.getNextKey
import com.pokemanager.utils.KeyUtils.getPrevKey
import com.pokemanager.utils.UrlUtils.getIdAtEndFromUrl
import retrofit2.HttpException
import java.io.IOException

//PageKeyed
class PokeSpecieItemsPagingSourceRemote(
    private val mainRepository: MainRepository
) : PagingSource<Int, PokeSpecieItemDomain>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokeSpecieItemDomain> {
        return try {

            val offset = params.key ?: POKEMON_PAGING_STARTING_KEY
            val itemsFromList = mainRepository.getPokemonItemsNetwork(
                limit = params.loadSize,
                offset = offset
            )

            val pokeSpecies = mutableListOf<PokeSpecieItemDomain>()
            for (item in itemsFromList.results) {
                val id = getIdAtEndFromUrl(item.url)
                if (id > LAST_VALID_POKEMON_NUMBER) {
                    break
                }
                val pokemonResponse = mainRepository.getPokemonItemByIdNetwork(id)
                val pokemonSpecieResponse = mainRepository.getPokemonSpecieItemByIdNetwork(id)
                val pokeSpecieItemDomain = pokemonResponse.toPokeSpecieItemDomain(pokemonSpecieResponse)
                pokeSpecies.add(pokeSpecieItemDomain)
            }

            val prevKey = getPrevKey(offset, params.loadSize)
            val nextKey = getNextKey(pokeSpecies.toMutableList())

            Page(
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