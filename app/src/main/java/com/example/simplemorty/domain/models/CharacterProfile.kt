package com.example.simplemorty.domain.models

import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.simplemorty.data.models.entity.character.cach.CharacterEntity
import com.example.simplemorty.utils.Converter
import com.google.gson.annotations.SerializedName

@TypeConverters(Converter::class)
data class CharacterProfile(
    val id: Int,
    val created: String? = "",
    val episode: List<String>? = listOf(),
    val gender: String? = "",
    val image: String? = "",
    val location: Location?,
    val name: String? = "",
    val homeland: Homeland?,
    val species: String? = "",
    val status: String? = "",
    val type: String? = "",
    val url: String? = "",
    var isFavorite: Boolean
)

