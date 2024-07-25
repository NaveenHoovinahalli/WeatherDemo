package com.example.weatherdemo.ui.weather

import com.example.weatherdemo.model.WeatherResponse
import com.example.weatherdemo.utils.Result
import kotlinx.coroutines.flow.Flow

sealed class WeatherUiStates {
    object Default : WeatherUiStates()
    object Launch : WeatherUiStates()

    class Loading : WeatherUiStates()
    class Success(val response : WeatherResponse) : WeatherUiStates()
    class SuccessFromDB(val response : WeatherResponse) : WeatherUiStates()

    class Error(val error : String) : WeatherUiStates()

}
data class WeatherUiState(
    val response : WeatherResponse? = null,
    val error: String
)