package com.example.simplemorty.domain.models

import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.simplemorty.data.models.entity.character.cach.CharacterEntity
import com.example.simplemorty.utils.ConvertersCharacter
import com.google.gson.annotations.SerializedName

@TypeConverters(ConvertersCharacter::class)
data class CharacterProfile(
    @PrimaryKey @field:SerializedName("id")
    val id: Int? = 0,
    @SerializedName("created")
    val created: String? = "",
    @SerializedName("episode")
    val episode: List<String>,
    @SerializedName("gender")
    val gender: String? = "",
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("location")
    val location: Location?,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("origin")
    val homeland: Homeland?,
    @SerializedName("species")
    val species: String? = "",
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("type")
    val type: String? = "",
    @SerializedName("url")
    val url: String? = "",
    @SerializedName("isFavorite")
    var isFavorite: Boolean
)

fun CharacterProfile.toCachedCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        created = created,
        episode = episode,
        gender = gender,
        id = id,
        image = image,
        location = location,
        name = name,
        homeland =homeland,
        species = species,
        status = status,
        type = type,
        url = url,
        isFavorite = isFavorite
    )
}