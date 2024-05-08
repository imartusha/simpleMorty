package com.example.simplemorty.data.models.entity.episode

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.utils.ConvertersEpisode

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

fun mapFromEntityToEpisode(episodeEntity: EpisodeEntity): Episode {
    return Episode(
        id = episodeEntity.id,
        airDate = episodeEntity.airDate,
        characters = episodeEntity.characters,
        created = episodeEntity.created,
        episode = episodeEntity.episode,
        name = episodeEntity.name,
        url = episodeEntity.url
    )
}

fun mapToListEpisode(episodes: List<EpisodeEntity>): List<Episode> {
    return episodes.map { episode ->
        mapFromEntityToEpisode(episode)
    }
}