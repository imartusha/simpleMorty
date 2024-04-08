//package com.example.simplemorty.data.database.episode
//
//import androidx.paging.PagingSource
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import com.example.simplemorty.data.models.entity.episode.CachedEpisodeEntity
//
//@Dao
//interface CachedEpisodeDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(episodes: List<CachedEpisodeEntity>)
//
//    @Query("SELECT * FROM cached_episodes")
//    fun getEpisodes(): PagingSource<Int, CachedEpisodeEntity>
//
//    @Query("DELETE FROM cached_episodes")
//    suspend fun clearEpisodes()
//
//    @Query("SELECT * FROM cached_episodes WHERE id = :id")
//    suspend fun getEpisodeById(id: Int): CachedEpisodeEntity?
//}
