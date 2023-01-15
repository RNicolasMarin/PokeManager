package com.pokemanager.data.remote

import com.pokemanager.data.remote.responses.PokemonItemResponse
import com.pokemanager.data.remote.responses.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeManagerApi {
    @GET("pokemon?")
    suspend fun getPokemonItemsNetwork(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : PokemonListResponse

    @GET("pokemon/{id}/")
    suspend fun getPokemonItemByIdNetwork(@Path("id") id: String) : PokemonItemResponse
}