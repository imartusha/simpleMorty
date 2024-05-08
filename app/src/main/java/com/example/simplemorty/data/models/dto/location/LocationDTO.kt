package com.example.simplemorty.data.models.dto.location

import com.example.simplemorty.data.models.dto.info.InfoDTO
import com.example.simplemorty.data.models.entity.LocationEntity
import com.example.simplemorty.domain.models.Info
import com.example.simplemorty.domain.models.Location
import com.google.gson.annotations.SerializedName

class LocationDTO(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
)

internal fun mapLocationDtoToDomain(locationDTO: LocationDTO): Location {
    return Location(
        created = locationDTO.created,
        dimension = locationDTO.dimension,
        id = locationDTO.id,
        name = locationDTO.name,
        residents = locationDTO.residents,
        type = locationDTO.type,
        url = locationDTO.url
    )
}

internal fun mapDtoToLocationEntity(locationDTO: LocationDTO): LocationEntity {
    return LocationEntity(
        created = locationDTO.created,
        dimension = locationDTO.dimension,
        id = locationDTO.id,
        name = locationDTO.name,
        residents = locationDTO.residents,
        type = locationDTO.type,
        url = locationDTO.url
    )
}

internal fun mapDomainToLocationDto(location: Location): LocationDTO {
    return LocationDTO(
        created = location.created,
        dimension = location.dimension,
        id = location.id,
        name = location.name,
        residents = location.residents,
        type = location.type,
        url = location.url
    )
}
fun mapDtoToLocationList(locations: List<LocationDTO>): List<Location> {
    return locations.map { location ->
        mapLocationDtoToDomain(location)
    }
}

fun mapDtoToEntityLocationList(locations: List<LocationDTO>): List<LocationEntity> {
    return locations.map { location ->
        mapDtoToLocationEntity(location)
    }
}