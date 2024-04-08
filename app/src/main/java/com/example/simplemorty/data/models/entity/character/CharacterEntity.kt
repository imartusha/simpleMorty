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
import com.google.gson.reflect.TypeToken


@Entity(tableName = "characters")
@TypeConverters(Converters::class)
class CharacterEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val created: String,
    val episode: List<String>,
    val gender: String,
    val image: String,
    val location: Location,
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

fun mapToCharacterEntity(characterProfile: CharacterProfile): CharacterEntity {
    return CharacterEntity(
        created = characterProfile.created,
        episode = characterProfile.episode,
        gender = characterProfile.gender,
        id = characterProfile.id,
        image = characterProfile.image,
        location = characterProfile.location,
        name = characterProfile.name,
        homeland = characterProfile.homeland,
        species = characterProfile.species,
        status = characterProfile.status,
        type = characterProfile.type,
        url = characterProfile.url
    )
}

fun mapEntityToCharacterProfile(characterEntity: CharacterEntity): CharacterProfile {
    return CharacterProfile(
        id = characterEntity.id,
        created = characterEntity.created,
        episode = characterEntity.episode,
        gender = characterEntity.gender,
        image = characterEntity.image,
        location = characterEntity.location,
        name = characterEntity.name,
        homeland = characterEntity.homeland,
        species = characterEntity.species,
        status = characterEntity.status,
        type = characterEntity.type,
        url = characterEntity.url
    )
}
fun mapFromDTOToCharacterEntity(characterDTO: CharacterDTO): CharacterEntity {
    return CharacterEntity(
        id = characterDTO.id,
        created = characterDTO.created,
        episode = characterDTO.episode,
        gender = characterDTO.gender,
        image = characterDTO.image,
        location =characterDTO.location,
        name = requireNotNull(characterDTO.name),
        homeland = requireNotNull(characterDTO.homeland),
        species = characterDTO.species,
        status = characterDTO.status,
        type = characterDTO.type,
        url = characterDTO.url
    )
}