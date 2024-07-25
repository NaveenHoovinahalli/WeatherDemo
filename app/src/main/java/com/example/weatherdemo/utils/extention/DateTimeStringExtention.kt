package com.example.weatherdemo.utils.extention


import java.util.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

fun Int.toUtcDateTimeString(): String {
    val dateTime = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(this.toLong()),
        ZoneId.of("Asia/Kolkata")
    )
    
    val formatter = DateTimeFormatter.ofPattern("MMM dd")
    return dateTime.format(formatter)
}

fun Int.toUtcTimeString(): String {
    val dateTime = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(this.toLong()),
        ZoneId.of("Asia/Kolkata")
    )

    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return dateTime.format(formatter)
}

fun Double.toCelsius(): Int {
    return (this - 273.15).roundToInt()
}

fun meterPerSecondToKilometerPerHour(meterPerSecond: Double): Int {
    return (meterPerSecond * 3.6).toInt()
}

fun isDayTime() : Boolean {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return !(currentHour >= 20 || currentHour < 6)
}