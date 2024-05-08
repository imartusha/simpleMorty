package com.example.simplemorty.data.models.entity.character

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.simplemorty.data.models.dto.character.CharacterDTO
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Homeland
import com.example.simplemorty.domain.models.Location
import com.example.simplemorty.utils.ConvertersCharacter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = "characters")
@TypeConverters(ConvertersCharacter::class)
class CharacterEntity(
    @PrimaryKey @field:SerializedName("id")
    val id: Int? = 0,
    @SerializedName("created")
    val created: String? = "",
    @SerializedName("episode")
    val episode: List<String>? = listOf(),
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

fun CharacterProfile.toCharacterEntity(): CharacterEntity {
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

fun CharacterEntity.toCharacterProfile(): CharacterProfile {
    return CharacterProfile(
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
fun CharacterDTO.toCharacterEntity(): CharacterEntity {
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