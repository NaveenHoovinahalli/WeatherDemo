package com.example.weatherdemo.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson


class Converters {

    @TypeConverter
    fun fromClouds(something: List<Something>) :String {
        return Gson().toJson(something)
    }

    @TypeConverter
    fun toClouds(something: String): List<Something> {
        return Gson().fromJson<List<Something>>(something, Something::class.java)
    }
}