package com.example.simplemorty.data.database.episode

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simplemorty.data.models.entity.episode.EpisodeEntity

@Dao
interface CachedEpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(episodes: List<EpisodeEntity>)

    @Query("SELECT * FROM episodes")
    fun getEpisodes(): PagingSource<Int, EpisodeEntity>

    @Query("DELETE FROM episodes")
    suspend fun clearEpisodes()

    @Query("SELECT * FROM episodes WHERE id = :id")
    suspend fun getEpisodesById(id: Int): EpisodeEntity?

    @Query("SELECT * FROM episodes WHERE id IN (:episodeIdsList)")
    fun getMultipleEpisode(episodeIdsList: List<String>): List<EpisodeEntity>
}