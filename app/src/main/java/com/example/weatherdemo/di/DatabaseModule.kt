package com.example.weatherdemo.di

import android.content.Context
import androidx.room.Room
import com.example.weatherdemo.data.db.WeatherDao
import com.example.weatherdemo.data.db.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): WeatherDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            WeatherDatabase::class.java,
            "weather_database"
        ).build()
    }

    @Provides
    fun provideWeatherDataDao(db: WeatherDatabase): WeatherDao {
        return db.weatherDao()
    }
}