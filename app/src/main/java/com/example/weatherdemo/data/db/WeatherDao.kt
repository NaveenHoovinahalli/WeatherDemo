package com.example.weatherdemo.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWeather(weather: Weather)

    @Query("SELECT * FROM weather_table")
     fun readAllDate(): Flow<List<Weather>>
}