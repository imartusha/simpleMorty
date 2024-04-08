package com.example.simplemorty.data.models.dto.episode

import com.example.simplemorty.data.models.entity.episode.EpisodeEntity
import com.example.simplemorty.data.models.response.Result
import com.example.simplemorty.domain.models.Episode
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
) : Result

internal fun mapDtoToEpisode(episodeDTO: EpisodeDTO): Episode {
    return Episode(
        airDate = episodeDTO.airDate,
        characters = episodeDTO.characters,
        created = episodeDTO.created,
        episode = episodeDTO.episode,
        id = episodeDTO.id,
        name = episodeDTO.name,
        url = episodeDTO.url
    )
}

internal fun mapDtoToEpisodeEntity(episodeDTO: EpisodeDTO): EpisodeEntity {
    return EpisodeEntity(
        airDate = episodeDTO.airDate,
        characters = episodeDTO.characters,
        created = episodeDTO.created,
        episode = episodeDTO.episode,
        id = episodeDTO.id,
        name = episodeDTO.name,
        url = episodeDTO.url
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
        mapDtoToEpisodeEntity(episodeDto)
    }
}

fun mapDtoToEpisodeList(episodes: List<EpisodeDTO>): List<Episode> {
    return episodes.map { episode ->
        mapDtoToEpisode(episode)
    }
}