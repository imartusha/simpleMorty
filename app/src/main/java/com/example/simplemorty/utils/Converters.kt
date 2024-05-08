package com.example.simplemorty.utils

import androidx.room.TypeConverter
import com.example.simplemorty.domain.models.Homeland
import com.example.simplemorty.domain.models.Location
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ConvertersCharacter {
    private val gson = Gson()

    @TypeConverter
    fun fromStringToList(value: String?): List<String> {
        val listType = object : TypeToken<List<String?>?>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromListToString(list: List<String?>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromJsonLocation(json: String?): Location? {
        return gson.fromJson(json, Location::class.java)
    }

    @TypeConverter
    fun toJson(location: Location?): String? {
        return gson.toJson(location)
    }


    @TypeConverter
    fun fromJsonHomeland(json: String?): Homeland? {
        return gson.fromJson(json, Homeland::class.java)
    }

    @TypeConverter
    fun toJson(homeland: Homeland?): String? {
        return gson.toJson(homeland)
    }
}
object ConvertersEpisode {
    private val gson = Gson()

    @TypeConverter
    fun fromStringToList(value: String?): List<String> {
        val listType = object : TypeToken<List<String?>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromListToString(list: List<String?>): String {
        return gson.toJson(list)
    }
}