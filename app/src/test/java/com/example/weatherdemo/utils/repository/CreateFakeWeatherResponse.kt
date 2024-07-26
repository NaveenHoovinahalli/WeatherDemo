package com.example.weatherdemo.utils.repository

import com.example.weatherdemo.model.*

fun createFakeWeatherResponse(): WeatherResponse {
    return WeatherResponse(
        coord = Coord(
            lon = 77.6804,
            lat = 12.954
        ),
        weather = listOf(
            Weather(
                main = "Rain",
                description = "light rain",
                icon = "10d"
            )
        ),
        base = "stations",
        main = Main(
            temp = 300.24,
            feels_like = 302.07,
            temp_min = 299.35,
            temp_max = 301.22,
            pressure = 1008,
            humidity = 69,
            sea_level = 1008,
            grnd_level = 911
        ),
        visibility = 6000,
        wind = Wind(
            speed = 6.17,
            deg = 240
        ),
        clouds = Clouds(
            all = 75
        ),
        dt = 1721728672,
        sys = Sys(
            type = 1,
            country = "IN",
            sunrise = 1721694763,
            sunset = 1721740709
        ),
        timezone = 19800,
        id = 1277333,
        name = "Bengaluru",
        cod = 200
    )
}
