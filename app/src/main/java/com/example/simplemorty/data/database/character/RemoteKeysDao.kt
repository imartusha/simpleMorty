package com.example.simplemorty.data.database.character

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simplemorty.data.models.entity.character.RemoteKeysEntity

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeysEntity: List<RemoteKeysEntity>)

    @Query("SELECT * FROM remote_keys WHERE repoId = :repoId")
    suspend fun remoteKeysRepoId(repoId: Int?): RemoteKeysEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()

    @Query("SELECT * FROM remote_keys ORDER BY repoId DESC LIMIT 1")
    suspend fun getLastRemoteKey(): RemoteKeysEntity?

}