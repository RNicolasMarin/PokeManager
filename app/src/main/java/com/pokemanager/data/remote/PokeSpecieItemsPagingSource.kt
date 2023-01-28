package com.pokemanager.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.data.mappers.toPokeSpecieItemDomain
import com.pokemanager.utils.Constants.LAST_VALID_POKEMON_NUMBER
import com.pokemanager.utils.Constants.POKEMON_PAGING_STARTING_KEY
import com.pokemanager.utils.Utils
import com.pokemanager.utils.Utils.getNextKey
import com.pokemanager.utils.Utils.getPrevKey
import retrofit2.HttpException
import java.io.IOException

//PageKeyed
class PokeSpecieItemsPagingSource(
    private val pokeManagerApi: PokeManagerApi
) : PagingSource<Int, PokeSpecieItemDomain>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokeSpecieItemDomain> {
        return try {

            val offset = params.key ?: POKEMON_PAGING_STARTING_KEY
            val itemsFromList = pokeManagerApi.getPokemonItemsNetwork(
                limit = params.loadSize,
                offset = offset
            )

            val pokemonList = mutableListOf<PokeSpecieItemDomain>()
            for (item in itemsFromList.results) {
                val id = Utils.getIdAtEndFromUrl(item.url)
                if (id.toInt() > LAST_VALID_POKEMON_NUMBER) {
                    break
                }
                val pokeSpecieItemResponse = pokeManagerApi.getPokemonItemByIdNetwork(id)
                val pokeSpecieItemDomain = pokeSpecieItemResponse.toPokeSpecieItemDomain()
                pokemonList.add(pokeSpecieItemDomain)
            }

            val prevKey = getPrevKey(offset, params.loadSize)
            val nextKey = getNextKey(pokemonList)

            Page(
                data = pokemonList,
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