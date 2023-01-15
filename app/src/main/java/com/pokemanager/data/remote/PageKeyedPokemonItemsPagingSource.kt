package com.pokemanager.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingState
import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.data.mappers.toPokeSpecieItemDomain
import com.pokemanager.utils.Constants.LAST_VALID_POKEMON_NUMBER
import com.pokemanager.utils.Constants.POKEMON_PAGING_STARTING_KEY
import com.pokemanager.utils.Utils
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.max

class PageKeyedPokemonItemsPagingSource(
    private val pokeManagerApi: PokeManagerApi
) : PagingSource<String, PokeSpecieItemDomain>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PokeSpecieItemDomain> {
        return try {

            val offset = params.key?.toInt() ?: POKEMON_PAGING_STARTING_KEY
            val itemsFromList = pokeManagerApi.getPokemonItemsNetwork(
                limit = params.loadSize,
                offset = offset
            )

            val pokemonList = mutableListOf<PokeSpecieItemDomain>()
            var lastId = offset
            for (item in itemsFromList.results!!) {
                val id = Utils.getIdAtEndFromUrl(item.url)
                if (/*id.toInt() != lastId + 1*/id.toInt() > LAST_VALID_POKEMON_NUMBER) {
                    break
                }
                val pokeSpecieItemResponse = pokeManagerApi.getPokemonItemByIdNetwork(id)
                val pokeSpecieItemDomain = pokeSpecieItemResponse.toPokeSpecieItemDomain()
                pokemonList.add(pokeSpecieItemDomain)
                lastId = id.toInt()
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

    private fun getNextKey(pokemonList: MutableList<PokeSpecieItemDomain>): String? {
        val last = pokemonList.lastOrNull() ?: return null
        if (last.id == null) return null

        return if (last.id!! >= LAST_VALID_POKEMON_NUMBER) {
            null
        } else {
            (last.id).toString()
        }
    }

    private fun getPrevKey(offset: Int, loadSize: Int): String? {
        return when (offset) {
            POKEMON_PAGING_STARTING_KEY -> null
            else -> when (val prevKey = ensureValidKey(key = offset - loadSize)) {
                // We're at the start, there's nothing more to load
                POKEMON_PAGING_STARTING_KEY -> POKEMON_PAGING_STARTING_KEY.toString()
                else -> prevKey.toString()
            }
        }
    }

    private fun ensureValidKey(key: Int) = max(POKEMON_PAGING_STARTING_KEY, key)

    override fun getRefreshKey(state: PagingState<String, PokeSpecieItemDomain>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.id.toString()
        }
    }
}