package com.example.weatherdemo.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "weather_table")
@TypeConverters(Converters::class)
data class WeatherDb(

    @PrimaryKey( autoGenerate = true)
    val id : Int =0,
    val name : String,
    val tem : String,
    val something: List<Something>
)

data class Something(
    val name : String,
    val age : Int
)