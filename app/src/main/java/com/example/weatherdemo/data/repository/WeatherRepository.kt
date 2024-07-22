package com.example.weatherdemo.data.repository


import com.example.weatherdemo.model.WeatherResponse
import com.example.weatherdemo.utils.Result
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeather(lat: Double, lang: Double, key : String): Flow<Result<WeatherResponse>>
}