package com.example.simplemorty.data.database.character

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.simplemorty.data.database.episode.EpisodeDao
import com.example.simplemorty.data.models.entity.character.CachedCharacterEntity
import com.example.simplemorty.data.models.entity.character.CharacterEntity
import com.example.simplemorty.data.models.entity.character.CharacterRemoteKeysEntity
import com.example.simplemorty.data.models.entity.episode.EpisodeEntity

const val MY_DATA_BASE = "my-database"

@Database(
    entities = [
        CharacterEntity::class,
        CachedCharacterEntity::class,
        CharacterRemoteKeysEntity::class,
        EpisodeEntity::class
    ],
    version = 1,
    exportSchema = true
)

abstract class CharactersDataBase : RoomDatabase() {

    abstract fun getCharacterDao(): CharacterDao
    abstract fun getEpisodeDao(): EpisodeDao
    abstract val remoteKeysDao: CharacterRemoteKeyDao
    abstract val cacheDao: CachedCharacterDao
}