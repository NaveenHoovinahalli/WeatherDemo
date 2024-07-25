package com.example.weatherdemo.data.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherdemo.model.WeatherResponse

@Database(entities = [WeatherResponse::class], version = 1)
@TypeConverters(ConvertersResponse::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}