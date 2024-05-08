package com.example.simplemorty.data.database.character

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.simplemorty.data.models.entity.character.cach.CachedCharacterEntity
import com.example.simplemorty.data.models.entity.character.FavoriteEntity
import com.example.simplemorty.data.models.entity.character.RemoteKeysEntity

const val MY_DATA_BASE = "my-database"

@Database(
    entities = [
        FavoriteEntity::class,
        CachedCharacterEntity::class,
        RemoteKeysEntity::class],
    version = 1,
    exportSchema = false
)

abstract class DataBase : RoomDatabase() {

    abstract val cacheDao: CachedCharacterDao
    abstract val favoriteDao: FavoriteCharacterDao
    abstract val remoteKeysDao: RemoteKeysDao
}