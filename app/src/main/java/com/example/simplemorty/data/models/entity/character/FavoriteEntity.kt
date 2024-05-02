package com.example.simplemorty.data.models.entity.character

import android.webkit.WebStorage.Origin
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Homeland
import com.example.simplemorty.domain.models.Location
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorite_entity")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    @field:SerializedName("id")
    val id: Int? = null,
    @SerializedName("created")
    val created: String? = "",
    @SerializedName("episode")
    val episode: List<String>? = listOf(),
    @SerializedName("gender")
    val gender: String? = "",
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("location")
    val location: Location?,
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

fun FavoriteEntity.toCharacterProfile(): CharacterProfile {
    return CharacterProfile(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        homeland = homeland,
        location = location,
        image = image,
        isFavorite = isFavorite
    )
}
fun CharacterProfile.toFavoriteEntity(): FavoriteEntity {
    return FavoriteEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        homeland = homeland,
        location = location,
        image = image,
        isFavorite = isFavorite
    )
}