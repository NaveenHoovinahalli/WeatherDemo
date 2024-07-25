package com.example.weatherdemo.utils.extention

import com.example.weatherdemo.R
import com.example.weatherdemo.utils.constants.MainWeatherConditions
import com.example.weatherdemo.utils.constants.WeatherConditions


fun getWeatherCondition(weatherCondition: String, isDarkTheme: Boolean): Int {
    return when (weatherCondition) {
        MainWeatherConditions.CLEAR ->
            if (isDarkTheme) R.drawable.clear_sky_day else R.drawable.clear_sky_night
        MainWeatherConditions.SNOW ->
            if (isDarkTheme) R.drawable.few_clouds_day else R.drawable.few_clouds_night
        MainWeatherConditions.CLOUDS ->
            if (isDarkTheme) R.drawable.broken_clouds_day else R.drawable.broken_clouds_night
        MainWeatherConditions.RAIN, MainWeatherConditions.DRIZZLE, MainWeatherConditions.THUNDERSTORM ->
            if (isDarkTheme) R.drawable.rain_day else R.drawable.rain_night
        else ->
            if (isDarkTheme) R.drawable.rain_day else R.drawable.rain_night
    }
}