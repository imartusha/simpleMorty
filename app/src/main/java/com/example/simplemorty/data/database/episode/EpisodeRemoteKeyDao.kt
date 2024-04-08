//package com.example.simplemorty.data.database.episode
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import com.example.simplemorty.data.models.entity.character.CharacterRemoteKeysEntity
//import com.example.simplemorty.data.models.entity.episode.EpisodeRemoteKeyEntitiy
//
//@Dao
//interface EpisodeRemoteKeyDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(remoteKeysEntity: List<EpisodeRemoteKeyEntitiy>)
//
//    @Query("SELECT * FROM remote_keys_episode WHERE repoId = :repoId")
//    suspend fun remoteKeysRepoId(repoId: Int): EpisodeRemoteKeyEntitiy?
//
//    @Query("DELETE FROM remote_keys_episode")
//    suspend fun clearRemoteKeys()
//
//    @Query("SELECT * FROM remote_keys_episode ORDER BY repoId DESC LIMIT 1")
//    suspend fun getLastRemoteKey(): EpisodeRemoteKeyEntitiy?
//
//}