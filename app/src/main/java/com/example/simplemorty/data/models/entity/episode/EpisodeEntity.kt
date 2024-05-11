package com.example.simplemorty.data.models.entity.episode

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.simplemorty.data.models.dto.episode.EpisodeDTO
import com.example.simplemorty.data.models.dto.episode.toEpisode
import com.example.simplemorty.data.models.dto.location.LocationDTO
import com.example.simplemorty.data.models.entity.location.LocationEntity
import com.example.simplemorty.data.models.entity.location.toLocationEntity
import com.example.simplemorty.data.models.response.CommonResponse
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.utils.Converter
import retrofit2.Response

@Entity(tableName = "episodes")
@TypeConverters(Converter::class)
class EpisodeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val airDate: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val name: String,
    val url: String
)

internal fun mapToEpisodeResponse(response: Response<CommonResponse<EpisodeDTO>>): Response<CommonResponse<Episode>> {
    val commonResponseDTO = response.body()
    val resultsDTO = commonResponseDTO?.results
    val resultsEntity = resultsDTO?.map { episodeDTO ->
        episodeDTO.toEpisode()
    }
    val commonResponseEntity = CommonResponse(info = null, results = resultsEntity)
    return Response.success(commonResponseEntity)
}

fun mapToCachedEpisodeEntity(episodes: List<EpisodeDTO>): List<EpisodeEntity> {
    return episodes.map { episodeDTO ->
        episodeDTO.toEpisodeEntity()
    }
}

fun EpisodeDTO.toEpisodeEntity(): EpisodeEntity {
    return EpisodeEntity(
        id = requireNotNull(id),
        airDate = requireNotNull(airDate),
        characters = requireNotNull(characters),
        created = requireNotNull(created),
        episode = requireNotNull(episode),
        name = requireNotNull(name),
        url = requireNotNull(url)
    )
}
fun Episode.toCachedEpisodeEntity(): EpisodeEntity {
    return EpisodeEntity(
        id = id,
        airDate = airDate,
        characters = characters,
        created = created,
        episode = episode,
        name = name,
        url = url
    )
}
fun EpisodeEntity.toEpisode(): Episode {
    return Episode(
        id = id,
        airDate = airDate,
        characters = characters,
        created = created,
        episode = episode,
        name = name,
        url = url
    )
}

fun mapToListEpisode(episodes: List<EpisodeEntity>): List<Episode> {
    return episodes.map { episode ->
        episode.toEpisode()
    }
}