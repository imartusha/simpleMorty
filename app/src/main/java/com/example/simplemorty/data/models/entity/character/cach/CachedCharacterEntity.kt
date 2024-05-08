package com.example.simplemorty.data.models.entity.character.cach

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.simplemorty.data.models.dto.character.CharacterDTO
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Homeland
import com.example.simplemorty.domain.models.Location
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = "cached_characters")
@TypeConverters(ConvertersCachedCharacterEntity::class)
data class CachedCharacterEntity(
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
    val homeland:Homeland?,
    @SerializedName("species")
    val species: String? = "",
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("type")
    val type: String? = "",
    @SerializedName("url")
    val url: String? = "",
    var isFavorite: Boolean

)
object ConvertersCachedCharacterEntity {
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


fun CharacterDTO.toCachedCharacterEntity(): CachedCharacterEntity {
    return CachedCharacterEntity(
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
fun CharacterProfile.toCachedCharacterEntity(): CachedCharacterEntity {
    return CachedCharacterEntity(
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
fun CachedCharacterEntity.toCharacterProfile(): CharacterProfile {
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

//fun mapToCachedCharacterEntity(characters: List<CharacterDTO>): List<CachedCharacterEntity> {
//    return characters.map { characterDTO ->
//        characterDTO.toCachedCharacterEntity()
//    }
//}
fun mapToCachedCharacterEntity(characters: List<CharacterProfile>): List<CachedCharacterEntity> {
    return characters.map { characterProfile ->
        characterProfile.toCachedCharacterEntity()
    }
}