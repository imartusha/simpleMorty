package com.example.simplemorty.data.models.entity.episode

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.simplemorty.data.models.dto.character.CharacterDTO
import com.example.simplemorty.data.models.dto.character.toCharacterProfile
import com.example.simplemorty.data.models.dto.episode.EpisodeDTO
import com.example.simplemorty.data.models.dto.episode.toEpisode
import com.example.simplemorty.data.models.response.CommonResponse
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.utils.ConvertersEpisode
import retrofit2.Response

@Entity(tableName = "episodes")
@TypeConverters(ConvertersEpisode::class)
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
fun mapToEntityEpisode(episode: Episode): EpisodeEntity {
    return EpisodeEntity(
        id = episode.id,
        airDate = episode.airDate,
        characters = episode.characters,
        created = episode.created,
        episode = episode.episode,
        name = episode.name,
        url = episode.url
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