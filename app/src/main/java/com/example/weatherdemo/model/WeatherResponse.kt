package com.example.weatherdemo.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weatherdemo.data.db.ConvertersResponse

@Entity(tableName = "weather_table")
@TypeConverters(ConvertersResponse::class)
data class WeatherResponse(
    @PrimaryKey( autoGenerate = true)
    val id: Int = 0,
    val base: String,
    @Embedded val clouds: Clouds,
    val cod: Int,
    @Embedded val coord: Coord,
    val dt: Int,
   @Embedded val main: Main,
    val name: String,
    @Embedded val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    @Embedded val wind: Wind
)

data class Clouds(
    val all: Int
)

data class Coord(
    val lat: Double,
    val lon: Double
)

data class Main(
    val feels_like: Double,
    val grnd_level: Int,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)

data class Sys(
    val country: String,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)

data class Weather(
    val description: String,
    val icon: String,
    val main: String
)

data class Wind(
    val deg: Int,
    val speed: Double
)
