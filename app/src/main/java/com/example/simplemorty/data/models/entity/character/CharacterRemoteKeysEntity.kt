package com.example.simplemorty.data.models.entity.character

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
class CharacterRemoteKeysEntity(
   @PrimaryKey(autoGenerate = false)
    val repoId: Int?,
    val prevKey: Int?,
    val nextKey: Int?
    //If you’re loading in the batch of 20 items and the current page is 4,
    // all these 20 items on page 4 will have a prevKey=3 and nextKey=5.
) {
}