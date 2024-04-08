//package com.example.simplemorty.data.models.entity.episode
//
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import androidx.room.TypeConverter
//import androidx.room.TypeConverters
//import com.example.simplemorty.data.models.dto.character.CharacterDTO
//import com.example.simplemorty.data.models.dto.episode.EpisodeDTO
//import com.example.simplemorty.data.models.entity.character.CachedCharacterEntity
//import com.example.simplemorty.data.models.entity.character.ConvertersCachedCharacterEntity
//import com.example.simplemorty.domain.models.Episode
//import com.example.simplemorty.domain.models.Homeland
//import com.example.simplemorty.domain.models.Location
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//
//@Entity(tableName = "cached_episodes")
//@TypeConverters(ConvertersCachedEpisodeEntity::class)
//class CachedEpisodeEntity (
//    @PrimaryKey(autoGenerate = false)
//    val id: Int,
//    val airDate: String,
//    val characters: List<String>,
//    val created: String,
//    val episode: String,
//    val name: String,
//    val url: String
//) {
//}
//object ConvertersCachedEpisodeEntity {
//    private val gson = Gson()
//
//    @TypeConverter
//    fun fromStringToList(value: String?): List<String> {
//        val listType = object : TypeToken<List<String?>?>() {}.type
//        return gson.fromJson(value, listType)
//    }
//
//    @TypeConverter
//    fun fromListToString(list: List<String?>?): String {
//        return gson.toJson(list)
//    }
//
//    @TypeConverter
//    fun fromJsonLocation(json: String?): Location? {
//        return gson.fromJson(json, Location::class.java)
//    }
//
//    @TypeConverter
//    fun toJson(location: Location?): String? {
//        return gson.toJson(location)
//    }
//
//
//    @TypeConverter
//    fun fromJsonHomeland(json: String?): Homeland? {
//        return gson.fromJson(json, Homeland::class.java)
//    }
//
//    @TypeConverter
//    fun toJson(homeland: Homeland?): String? {
//        return gson.toJson(homeland)
//    }
//}
//fun mapToCashedEntityEpisode(episode: EpisodeDTO): CachedEpisodeEntity {
//    return CachedEpisodeEntity(
//        id = episode.id,
//        airDate = episode.airDate,
//        characters = episode.characters,
//        created = episode.created,
//        episode = episode.episode,
//        name = episode.name,
//        url = episode.url
//    )
//}
//fun mapCachedToEpisode(episode: CachedEpisodeEntity): Episode {
//    return Episode(
//        id = episode.id,
//        airDate = episode.airDate,
//        characters = episode.characters,
//        created = episode.created,
//        episode = episode.episode,
//        name = episode.name,
//        url = episode.url
//    )
//}
//fun mapToCachedEpisodeEntity(episodes: List<EpisodeDTO>): List<CachedEpisodeEntity> {
//    return episodes.map { episodeDTO ->
//        mapToCashedEntityEpisode(episodeDTO)
//    }
//}