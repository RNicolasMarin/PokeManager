package com.pokemanager.di

import android.content.Context
import android.content.SharedPreferences
import com.pokemanager.data.local.PokeManagerDatabase
import com.pokemanager.data.preferences.PokeManagerPreferences
import com.pokemanager.data.preferences.PokeManagerPreferencesImpl
import com.pokemanager.utils.Constants.baseUrl
import com.pokemanager.data.remote.PokeManagerApi
import com.pokemanager.data.repositories.MainRepository
import com.pokemanager.use_cases.GetPokeSpecieDetailUseCase
import com.pokemanager.use_cases.GetPokeSpecieItemsUseCase
import com.pokemanager.utils.Constants.SHARED_PREFERENCES_NAME
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
    fun provideSharedPreferences(@ApplicationContext app: Context) =
        app.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun providePokeManagerPreferences(sharedPreferences: SharedPreferences): PokeManagerPreferences =
        PokeManagerPreferencesImpl(sharedPreferences)

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
    fun getMainRepository(
        pokeManagerApi: PokeManagerApi,
        pokeDatabase: PokeManagerDatabase
    ): MainRepository {
        return MainRepository(pokeManagerApi, pokeDatabase)
    }

    @Singleton
    @Provides
    fun getGetPokeSpecieItemsUseCase(
        mainRepository: MainRepository
    ): GetPokeSpecieItemsUseCase {
        return GetPokeSpecieItemsUseCase(mainRepository)
    }

    @Singleton
    @Provides
    fun getGetPokeSpecieDetailUseCase(
        mainRepository: MainRepository
    ): GetPokeSpecieDetailUseCase {
        return GetPokeSpecieDetailUseCase(mainRepository)
    }
}