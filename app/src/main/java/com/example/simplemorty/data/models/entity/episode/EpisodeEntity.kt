package com.example.simplemorty.data.models.entity.episode

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.simplemorty.data.models.entity.character.Converters
import com.example.simplemorty.domain.models.Episode
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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

object ConvertersEpisode {
    private val gson = Gson()

    @TypeConverter
    fun fromStringToList(value: String?): List<String> {
        val listType = object : TypeToken<List<String?>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromListToString(list: List<String?>): String {
        return gson.toJson(list)
    }

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