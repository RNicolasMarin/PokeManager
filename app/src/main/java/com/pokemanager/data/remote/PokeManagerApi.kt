package com.pokemanager.data.remote

import com.pokemanager.data.remote.responses.*
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
    suspend fun getPokemonItemByIdNetwork(@Path("id") id: Int) : PokemonItemResponse

    @GET("pokemon/{id}/")
    suspend fun getPokemonDetailByIdNetwork(@Path("id") id: Int) : PokemonDetailResponse

    @GET("pokemon-species/{id}/")
    suspend fun getPokemonSpecieItemByIdNetwork(@Path("id") id: Int) : PokemonSpecieItemResponse

    @GET("pokemon-species/{id}/")
    suspend fun getPokemonSpecieDetailByIdNetwork(@Path("id") id: Int) : PokemonSpecieDetailResponse
}