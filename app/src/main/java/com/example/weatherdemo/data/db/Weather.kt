package com.example.weatherdemo.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class Weather(

    @PrimaryKey( autoGenerate = true)
    val id : Int,
    val name : String,
    val tem : String
)
