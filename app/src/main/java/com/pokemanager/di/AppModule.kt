package com.pokemanager.di

import android.content.Context
import com.pokemanager.data.local.PokeManagerDatabase
import com.pokemanager.utils.Constants.baseUrl
import com.pokemanager.data.remote.PokeManagerApi
import com.pokemanager.use_cases.GetPokeSpecieItemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun getPokeManagerDatabase(@ApplicationContext appContext: Context): PokeManagerDatabase {
        return PokeManagerDatabase.getInstance(appContext)
    }

    @Singleton
    @Provides
    fun getGetPokeSpecieItemsUseCase(
        pokeManagerApi: PokeManagerApi,
        pokeDatabase: PokeManagerDatabase
    ): GetPokeSpecieItemsUseCase {
        return GetPokeSpecieItemsUseCase(pokeManagerApi, pokeDatabase)
    }
}