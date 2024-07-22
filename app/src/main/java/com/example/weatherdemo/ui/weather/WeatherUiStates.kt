package com.example.weatherdemo.ui.weather

import com.example.weatherdemo.model.WeatherResponse
import com.example.weatherdemo.utils.Result
import kotlinx.coroutines.flow.Flow

sealed class WeatherUiStates {
    object Default : WeatherUiStates()
    class Loading(state : Boolean) : WeatherUiStates()
    class Success(val response : Result<WeatherResponse>) : WeatherUiStates()
    class Error(error : Boolean) : WeatherUiStates()

}
data class WeatherUiState(
    val response : WeatherResponse? = null
)