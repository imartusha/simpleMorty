package com.example.simplemorty.data.models.dto.episode

import com.example.simplemorty.domain.models.Episode
import com.google.gson.annotations.SerializedName
import retrofit2.Response

class EpisodeDTO(

    @SerializedName("air_date")
    val airDate: String? = "",
    @SerializedName("characters")
    val characters: List<String>? = listOf(),
    @SerializedName("created")
    val created: String? = "",
    @SerializedName("episode")
    val episode: String? = "",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("url")
    val url: String? = ""
)

internal fun mapToEpisodeResponse(responseDTO: Response<EpisodeDTO>): Response<Episode> {
    val episodeDTO = responseDTO.body()
    val episode = episodeDTO?.toEpisode()
    return Response.success(episode)
}

internal fun EpisodeDTO.toEpisode(): Episode {
    return Episode(
        airDate = requireNotNull(airDate),
        characters = requireNotNull(characters),
        created = requireNotNull(created),
        episode = requireNotNull(episode),
        id = requireNotNull(id),
        name = requireNotNull(name),
        url = requireNotNull(url)
    )
}
fun mapDtoToEpisodeList(episodes: List<EpisodeDTO>): List<Episode> {
    return episodes.map { episodeDTO ->
        episodeDTO.toEpisode()
    }
}