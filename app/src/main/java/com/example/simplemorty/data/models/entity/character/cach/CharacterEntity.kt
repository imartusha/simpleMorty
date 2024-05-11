package com.example.simplemorty.data.models.entity.character.cach

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.simplemorty.data.models.dto.character.CharacterDTO
import com.example.simplemorty.data.models.dto.character.toCharacterProfile
import com.example.simplemorty.data.models.response.CommonResponse
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Homeland
import com.example.simplemorty.domain.models.Location
import com.example.simplemorty.utils.Converter
import com.google.gson.annotations.SerializedName
import retrofit2.Response

@Entity(tableName = "cached_characters")
@TypeConverters(Converter::class)
data class CharacterEntity(
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
    var isFavorite: Boolean
)

internal fun mapToCharacterProfileResponse(response: Response<CommonResponse<CharacterDTO>>): Response<CommonResponse<CharacterProfile>> {
    val commonResponseDTO = response.body()
    val resultsDTO = commonResponseDTO?.results
    val resultsEntity = resultsDTO?.map { characterDTO ->
        characterDTO.toCharacterProfile()
    }
    val commonResponseEntity = CommonResponse(info = null, results = resultsEntity)
    return Response.success(commonResponseEntity)
}

internal fun mapToCachedCharacterResponse(response: Response<CommonResponse<CharacterDTO>>): Response<CommonResponse<CharacterEntity>> {
    val commonResponseDTO = response.body()
    val resultsDTO = commonResponseDTO?.results
    val resultsEntity = resultsDTO?.map { characterDTO ->
        characterDTO.toCachedCharacterEntity()
    }
    val commonResponseEntity = CommonResponse(info = null, results = resultsEntity)
    return Response.success(commonResponseEntity)
}

fun CharacterDTO.toCachedCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        created = created,
        episode = episode,
        gender = gender,
        id = id,
        image = image,
        location = location,
        name = name,
        homeland = homeland,
        species = species,
        status = status,
        type = type,
        url = url,
        isFavorite = isFavorite
    )
}

fun CharacterProfile.toCachedCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        created = created,
        episode = episode,
        gender = gender,
        id = id,
        image = image,
        location = location,
        name = name,
        homeland = homeland,
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
        episode = requireNotNull(episode),
        gender = gender,
        id = id,
        image = image,
        location = location,
        name = name,
        homeland = homeland,
        species = species,
        status = status,
        type = type,
        url = url,
        isFavorite = isFavorite
    )
}

fun fromProfileToCachedCharacterEntity(characters: List<CharacterProfile>): List<CharacterEntity> {
    return characters.map { characterProfile ->
        characterProfile.toCachedCharacterEntity()
    }
}
fun mapToCachedCharacterEntity(characters: List<CharacterDTO>): List<CharacterEntity> {
    return characters.map { characterDTO ->
        characterDTO.toCachedCharacterEntity()
    }
}