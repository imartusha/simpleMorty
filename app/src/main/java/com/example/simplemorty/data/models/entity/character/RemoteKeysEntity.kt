package com.example.simplemorty.data.models.entity.character

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey val repoId: Int?,
    val prevKey: Int?,
    val nextKey: Int?,
)