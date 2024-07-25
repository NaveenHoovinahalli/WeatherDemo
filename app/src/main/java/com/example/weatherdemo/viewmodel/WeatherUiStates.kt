package com.example.weatherdemo.viewmodel

import com.example.weatherdemo.model.WeatherResponse

sealed class WeatherUiStates {
    object Default : WeatherUiStates()
    object Launch : WeatherUiStates()
    class Loading : WeatherUiStates()
    class Success(val response: WeatherResponse) : WeatherUiStates()
    class SuccessFromDB(val response: WeatherResponse) : WeatherUiStates()
    class Error(val error: String) : WeatherUiStates()

}