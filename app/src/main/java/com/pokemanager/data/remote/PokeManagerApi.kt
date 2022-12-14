package com.pokemanager.data.remote

import com.pokemanager.data.remote.responses.PokemonItemResponse
import com.pokemanager.data.remote.responses.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeManagerApi {
    @GET("pokemon?offset=0&limit=50")
    suspend fun getPokemonItemsNetwork() : PokemonListResponse

    @GET("pokemon/{id}/")
    suspend fun getPokemonItemByIdNetwork(@Path("id") id: String) : PokemonItemResponse
}