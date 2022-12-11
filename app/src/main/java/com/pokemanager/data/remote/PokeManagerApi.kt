package com.pokemanager.data.remote

import com.pokemanager.data.remote.responses.item.PokemonNetworkItem
import retrofit2.Response
import retrofit2.http.GET

interface PokeManagerApi {
    @GET("pokemon?offset=0&limit=50")
    suspend fun getPokemonNetworkItems() : Response<List<PokemonNetworkItem>>
}