package com.example.simplemorty.data.models.entity.character

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
    val id: Int,
    val name: String,
    val created: String,
    val episode: List<String>,
    val gender: String,
    val image: String,
    val location: Location,
    val homeland: Homeland?,
    val species: String,
    val status: String,
    val type: String,
    val url: String
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


fun mapToCachedCharacterEntity(characterDTO: CharacterDTO): CachedCharacterEntity {
    return CachedCharacterEntity(
        id = characterDTO.id,
        name = requireNotNull(characterDTO.name),
        status = characterDTO.status,
        species = characterDTO.species,
        type = characterDTO.type,
        gender = characterDTO.gender,
        created = characterDTO.created,
        episode = characterDTO.episode,
        image = characterDTO.image,
        location = characterDTO.location,
        homeland = requireNotNull(characterDTO.homeland),
        url = characterDTO.url
    )
}
fun mapCachedToCharacterProfile(cachedCharacterEntity: CachedCharacterEntity): CharacterProfile {
    return CharacterProfile(
        id = cachedCharacterEntity.id,
        name = requireNotNull(cachedCharacterEntity.name),
        status = cachedCharacterEntity.status,
        species = cachedCharacterEntity.species,
        type = cachedCharacterEntity.type,
        gender = cachedCharacterEntity.gender,
        created = cachedCharacterEntity.created,
        episode = cachedCharacterEntity.episode,
        image = cachedCharacterEntity.image,
        location = cachedCharacterEntity.location,
        homeland = requireNotNull(cachedCharacterEntity.homeland),
        url = cachedCharacterEntity.url
    )
}

fun mapToCachedCharacterEntity(characters: List<CharacterDTO>): List<CachedCharacterEntity> {
    return characters.map { characterDTO ->
        mapToCachedCharacterEntity(characterDTO)
    }
}