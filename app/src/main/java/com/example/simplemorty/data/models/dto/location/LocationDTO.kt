package com.example.simplemorty.data.models.dto.location

import com.example.simplemorty.domain.models.Location
import com.google.gson.annotations.SerializedName
import retrofit2.Response

class LocationDTO(

    @SerializedName("created")
    val created: String? = "",
    @SerializedName("dimension")
    val dimension: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("residents")
    val residents: List<String>? = listOf(),
    @SerializedName("type")
    val type: String? = "",
    @SerializedName("url")
    val url: String? = ""
)

internal fun mapToLocationResponse(responseDTO: Response<LocationDTO>): Response<Location> {
    val episodeDTO = responseDTO.body()
    val episode = episodeDTO?.toLocation()
    return Response.success(episode)
}

internal fun LocationDTO.toLocation(): Location {
    return Location(
        id = requireNotNull(id),
        name = requireNotNull(name),
        created = requireNotNull(created),
        dimension = requireNotNull(dimension),
        residents = requireNotNull( residents),
        type = requireNotNull(type),
        url = requireNotNull(url)
    )
}

