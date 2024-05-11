package com.example.simplemorty.data.database.location

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simplemorty.data.models.entity.location.RemoteKeysLocation

@Dao
interface RemoteKeysLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeysLocation: List<RemoteKeysLocation>)

    @Query("SELECT * FROM remote_keys_location WHERE repoId = :repoId")
    suspend fun remoteKeysRepoId(repoId: Int?): RemoteKeysLocation?

    @Query("DELETE FROM remote_keys_location")
    suspend fun clearRemoteKeys()

    @Query("SELECT * FROM remote_keys_location ORDER BY repoId DESC LIMIT 1")
    suspend fun getLastRemoteKey(): RemoteKeysLocation?
}