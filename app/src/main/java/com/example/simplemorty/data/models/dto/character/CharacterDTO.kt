package com.example.simplemorty.data.models.dto.character

import androidx.room.PrimaryKey
import com.example.simplemorty.data.models.dto.info.InfoDTO
import com.example.simplemorty.data.models.entity.character.CharacterEntity
import com.example.simplemorty.data.models.entity.character.toCharacterEntity
import com.example.simplemorty.data.models.entity.character.toCharacterProfile
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Location
import com.example.simplemorty.domain.models.Homeland
import com.example.simplemorty.domain.models.Info
import com.google.gson.annotations.SerializedName



class CharacterDTO(
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

internal fun CharacterDTO.toCharacterProfile(): CharacterProfile {
    return CharacterProfile(
        id = id,
        name = requireNotNull(name),
        status = status,
        species = species,
        type = type,
        gender = gender,
        homeland = requireNotNull(homeland),
        location = requireNotNull(location),
        image = image,
        isFavorite = isFavorite
    )
}

internal fun CharacterDTO.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = requireNotNull(name),
        status = status,
        species = species,
        type = type,
        gender = gender,
        homeland = requireNotNull(homeland),
        location = requireNotNull(location),
        image = image,
        isFavorite = isFavorite
    )
}

internal fun CharacterProfile.toCharacterDto(): CharacterDTO {
    return CharacterDTO(
        id = id,
        name = requireNotNull(name),
        status = status,
        species = species,
        type = type,
        gender = gender,
        homeland = requireNotNull(homeland),
        location = requireNotNull(location),
        image = image,
        isFavorite = isFavorite
    )
}

fun mapDTOsToCharacterProfiles(characters: List<CharacterDTO>): List<CharacterProfile> {
    return characters.map { characterDTO ->
        characterDTO.toCharacterProfile()
    }
}

fun mapEntitiesToCharacterProfiles(characters: List<CharacterEntity>): List<CharacterProfile> {
    return characters.map { characterEntity ->
        characterEntity.toCharacterProfile()
    }
}

fun mapProfilesToCharacterEntities(characters: List<CharacterProfile>): List<CharacterEntity> {
    return characters.map { characterProfile ->
        characterProfile.toCharacterEntity()
    }
}

fun mapDTOsToCharacterEntities(characters: List<CharacterDTO>): List<CharacterEntity> {
    return characters.map { characterDTO ->
        characterDTO.toCharacterEntity()
    }
}