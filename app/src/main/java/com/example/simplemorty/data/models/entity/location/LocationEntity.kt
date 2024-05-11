package com.example.simplemorty.data.models.entity.location

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.simplemorty.data.models.dto.character.CharacterDTO
import com.example.simplemorty.data.models.dto.character.toCharacterProfile
import com.example.simplemorty.data.models.dto.episode.EpisodeDTO
import com.example.simplemorty.data.models.dto.episode.toEpisode
import com.example.simplemorty.data.models.dto.location.LocationDTO
import com.example.simplemorty.data.models.dto.location.toLocation
import com.example.simplemorty.data.models.entity.character.cach.CharacterEntity
import com.example.simplemorty.data.models.entity.character.cach.toCachedCharacterEntity
import com.example.simplemorty.data.models.response.CommonResponse
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.models.Location
import com.example.simplemorty.utils.Converter
import retrofit2.Response

@Entity(tableName = "locations")
@TypeConverters(Converter::class)
data class LocationEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val created: String,
    val dimension: String,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
)

internal fun mapToLocationCommonResponse(response: Response<CommonResponse<LocationDTO>>): Response<CommonResponse<Location>> {
    val commonResponseDTO = response.body()
    val resultsDTO = commonResponseDTO?.results
    val resultsEntity = resultsDTO?.map { characterDTO ->
        characterDTO.toLocation()
    }
    val commonResponseEntity = CommonResponse(info = null, results = resultsEntity)
    return Response.success(commonResponseEntity)
}

fun mapToCachedLocationEntity(locations: List<LocationDTO>): List<LocationEntity> {
    return locations.map { locationDTO ->
        locationDTO.toLocationEntity()
    }
}

fun LocationDTO.toLocationEntity(): LocationEntity {
    return LocationEntity(
        id = requireNotNull(id),
        name = requireNotNull(name),
        created = requireNotNull(created),
        dimension = requireNotNull(dimension),
        residents = requireNotNull( residents),
        type = requireNotNull(type),
        url = requireNotNull(url)
    )
}

fun Location.toCachedLocationEntity(): LocationEntity {
    return LocationEntity(
        id = id,
        name = name,
        created = created,
        dimension = dimension,
        residents = residents,
        type = type,
        url = url
    )
}

fun LocationEntity.toLocation(): Location {
    return Location(
        id = id,
        name = name,
        created = created,
        dimension = dimension,
        residents = residents?: listOf(),
        type = type,
        url = url
    )
}






