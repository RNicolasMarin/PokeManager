package com.pokemanager.di

import com.pokemanager.utils.Constants.baseUrl
import com.pokemanager.data.remote.PokeManagerApi
import com.pokemanager.use_cases.GetPokeSpecieItemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getPokeManagerApi(): PokeManagerApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeManagerApi::class.java)
    }

    @Singleton
    @Provides
    fun getGetPokeSpecieItemsUseCase(
        pokeManagerApi: PokeManagerApi
    ): GetPokeSpecieItemsUseCase {
        return GetPokeSpecieItemsUseCase(pokeManagerApi)
    }
}