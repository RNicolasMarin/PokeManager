package com.pokemanager.use_cases

import android.util.Log
import com.pokemanager.data.DataAccessMode
import com.pokemanager.data.domain.PokeSpecieItemDomain
import com.pokemanager.data.mappers.toPokeSpecieItemDomain
import com.pokemanager.data.remote.PokeManagerApi
import com.pokemanager.utils.DataState
import com.pokemanager.utils.Utils.getIdAtEndFromUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetPokeSpecieItemsUseCase(
    private val pokeManagerApi: PokeManagerApi
) {

    operator fun invoke(
        dataAccessMode: DataAccessMode
    ): Flow<DataState<MutableList<PokeSpecieItemDomain>>> = flow {
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
                val itemsFromList = pokeManagerApi.getPokemonItemsNetwork().results ?: return@flow
                val pokemonList = mutableListOf<PokeSpecieItemDomain>()
                for (item in itemsFromList) {
                    val id = getIdAtEndFromUrl(item.url)
                    val pokeSpecieItemResponse = pokeManagerApi.getPokemonItemByIdNetwork(id)
                    val pokeSpecieItemDomain = pokeSpecieItemResponse.toPokeSpecieItemDomain()
                    Log.d("GetPokeSpecieItems", "$pokeSpecieItemDomain")
                    pokemonList.add(pokeSpecieItemDomain)
                }
                emit(DataState.Success(pokemonList))
            }
        }
    }
}