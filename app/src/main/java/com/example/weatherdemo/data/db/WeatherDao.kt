package com.example.weatherdemo.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherdemo.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWeather(weather: WeatherResponse)

    @Query("SELECT * FROM weather_table")
     fun readAllDate(): Flow<List<WeatherResponse>>

//     @Query("DELETE  FROM weather_table")
//     fun  deleteTable()

}