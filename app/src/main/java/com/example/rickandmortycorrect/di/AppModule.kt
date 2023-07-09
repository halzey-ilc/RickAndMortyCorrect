package com.example.rickandmortycorrect.di

import android.app.Application
import androidx.room.Room
import com.example.rickandmortycorrect.data.local.RickAndMortyDatabase
import com.example.rickandmortycorrect.data.remote.RickAndMortyApi
import com.example.rickandmortycorrect.data.repository.RickAndMortyImpl
import com.example.rickandmortycorrect.domain.repository.RickAndMortyRepository
import com.example.rickandmortycorrect.utils.Constant.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRickyAndMortyApi(): RickAndMortyApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(RickAndMortyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRickAndMortyRepository(
        api: RickAndMortyApi,
        db: RickAndMortyDatabase
    ): RickAndMortyRepository {
        return RickAndMortyImpl(api, db.rickAndMortyDao)
    }

    @Provides
    @Singleton
    fun provideRickAndMortyDatabase(app: Application): RickAndMortyDatabase {
        return Room.databaseBuilder(app, RickAndMortyDatabase::class.java, "FavoriteDatabase")
            .build()
    }

}