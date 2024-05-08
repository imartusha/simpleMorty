package com.example.simplemorty.data.models.dto.episode

import android.os.Parcelable
import com.example.simplemorty.data.models.entity.episode.EpisodeEntity
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.models.Info
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName


class EpisodeDTO(

    @SerializedName("air_date")
    val airDate: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)

internal fun EpisodeDTO.toEpisode(): Episode {
    return Episode(
        airDate = airDate,
        characters = characters,
        created = created,
        episode = episode,
        id = id,
        name = name,
        url = url
    )
}

internal fun EpisodeDTO.toEpisodeEntity(): EpisodeEntity {
    return EpisodeEntity(
        airDate = airDate,
        characters = characters,
        created = created,
        episode = episode,
        id = id,
        name = name,
        url = url
    )
}

internal fun mapEpisodeToDTO(episode: Episode): EpisodeDTO {
    return EpisodeDTO(
        airDate = episode.airDate,
        characters = episode.characters,
        created = episode.created,
        episode = episode.episode,
        id = episode.id,
        name = episode.name,
        url = episode.url
    )
}

fun mapDtoToEntityEpisodeList(episodesDto: List<EpisodeDTO>): List<EpisodeEntity> {
    return episodesDto.map { episodeDto ->
        episodeDto.toEpisodeEntity()
    }
}

fun mapDtoToEpisodeList(episodes: List<EpisodeDTO>): List<Episode> {
    return episodes.map { episodeDTO ->
        episodeDTO.toEpisode()
    }
}