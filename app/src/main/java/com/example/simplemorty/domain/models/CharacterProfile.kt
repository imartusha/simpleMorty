package com.example.simplemorty.domain.models

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.simplemorty.data.models.entity.character.Converters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@TypeConverters(Converters::class)
data class CharacterProfile(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val homeland: Homeland,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) {
}
object Converters {
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

