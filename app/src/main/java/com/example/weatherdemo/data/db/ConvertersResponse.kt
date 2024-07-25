package com.example.weatherdemo.data.db

import androidx.room.TypeConverter
import com.example.weatherdemo.model.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ConvertersResponse {



    @TypeConverter
    fun fromDeveloperList(countryLang: List<Weather?>?): String? {
        val type = object : TypeToken<List<Weather>>() {}.type
        return Gson().toJson(countryLang, type)
    }
    @TypeConverter
    fun toDeveloperList(countryLangString: String?): List<Weather>? {
        val type = object : TypeToken<List<Weather>>() {}.type
        return Gson().fromJson<List<Weather>>(countryLangString, type)
    }
}