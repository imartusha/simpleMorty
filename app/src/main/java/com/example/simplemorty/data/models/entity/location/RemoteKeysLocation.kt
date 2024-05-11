package com.example.simplemorty.data.models.entity.location

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "remote_keys_location")
class RemoteKeysLocation(
    @PrimaryKey
    val repoId: Int?,
    val prevKey: Int?,
    val nextKey: Int?,
)