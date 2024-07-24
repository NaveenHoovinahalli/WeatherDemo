package com.example.weatherdemo.utils.extention

import androidx.compose.runtime.MutableState
import com.example.weatherdemo.R
import com.example.weatherdemo.utils.constants.WeatherConditions
import com.example.weatherdemo.utils.constants.WeatherConditions.CLEAR_SKY


fun getWeatherCondition(weatherCondition: String, isDarkTheme: Boolean): Int {
    return when (weatherCondition) {
        WeatherConditions.CLEAR_SKY ->
            if (isDarkTheme) R.drawable.clear_sky_day else R.drawable.clear_sky_night
        WeatherConditions.FEW_CLOUDS ->
            if (isDarkTheme) R.drawable.few_clouds_day else R.drawable.few_clouds_night
        WeatherConditions.SCATTERED_CLOUDS ->
            R.drawable.scattered_clouds
        WeatherConditions.BROKEN_CLOUDS ->
            if (isDarkTheme) R.drawable.broken_clouds_day else R.drawable.broken_clouds_night
        WeatherConditions.SHOWER_RAIN, WeatherConditions.RAIN, WeatherConditions.THUNDERSTORM ->
            if (isDarkTheme) R.drawable.rain_day else R.drawable.rain_night
        WeatherConditions.SNOW, WeatherConditions.MIST ->
            if (isDarkTheme) R.drawable.mist_day else R.drawable.mist_night
        else ->
            if (isDarkTheme) R.drawable.clear_sky_day else R.drawable.clear_sky_night
    }
}