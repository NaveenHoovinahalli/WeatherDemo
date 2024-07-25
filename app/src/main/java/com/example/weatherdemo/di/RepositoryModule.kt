package com.example.weatherdemo.di

import com.example.weatherdemo.data.network.WeatherApiService
import com.example.weatherdemo.data.repository.WeatherRepository
import com.example.weatherdemo.data.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object  RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(weatherApi: WeatherApiService): WeatherRepository =
        WeatherRepositoryImpl(weatherApi)
}