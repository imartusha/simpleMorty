package com.example.simplemorty.data.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.simplemorty.domain.models.Location

@Entity(tableName = "locations")
class LocationEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val created: String,
    val dimension: String,
    val residents: List<String>,
    val type: String,
    val url: String
) {

}
fun mapToEntityLocation(location: Location): LocationEntity {
    return LocationEntity(
        id = location.id,
        name = location.name,
        created = location.created,
        dimension = location.dimension,
        residents = location.residents,
        type = location.type,
        url = location.url
    )
}

fun mapFromEntityToLocation(locationEntity: LocationEntity): Location {
    return Location(
        id = locationEntity.id,
        name = locationEntity.name,
        created = locationEntity.created,
        dimension = locationEntity.dimension,
        residents = locationEntity.residents,
        type = locationEntity.type,
        url = locationEntity.url
    )
}