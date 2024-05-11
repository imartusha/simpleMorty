package com.example.simplemorty.data.database.character

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.simplemorty.data.database.episode.CachedEpisodeDao
import com.example.simplemorty.data.database.location.CachedLocationDao
import com.example.simplemorty.data.database.location.RemoteKeysLocationDao
import com.example.simplemorty.data.models.entity.character.cach.CharacterEntity
import com.example.simplemorty.data.models.entity.character.FavoriteEntity
import com.example.simplemorty.data.models.entity.character.RemoteKeysEntity
import com.example.simplemorty.data.models.entity.episode.EpisodeEntity
import com.example.simplemorty.data.models.entity.location.LocationEntity
import com.example.simplemorty.data.models.entity.location.RemoteKeysLocation

const val MY_DATA_BASE = "my-database"

@Database(
    entities = [
        FavoriteEntity::class,
        CharacterEntity::class,
        RemoteKeysEntity::class,
        RemoteKeysLocation::class,
        EpisodeEntity::class,
        LocationEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class DataBase : RoomDatabase() {

    abstract val cacheCharacterDao: CachedCharacterDao
    abstract val favoriteDao: FavoriteCharacterDao
    abstract val remoteKeysDao: RemoteKeysDao
    abstract val remoteKeysLocationDao: RemoteKeysLocationDao
    abstract val cachedEpisodeDao: CachedEpisodeDao
    abstract val cachedLocationDao: CachedLocationDao
}