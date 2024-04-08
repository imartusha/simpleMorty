package com.example.simplemorty.data.database.episode

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simplemorty.data.models.entity.character.CharacterEntity
import com.example.simplemorty.data.models.entity.episode.EpisodeEntity

@Dao
interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episodeEntity: EpisodeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEpisodes(episodes: List<EpisodeEntity>)

    @Query("SELECT * FROM episodes")
    fun getAllEpisodes(): List<EpisodeEntity>

//    @Query("SELECT * FROM characters")
//    fun pagingSource(): PagingSource<Int, CharacterProfile>

    @Query("SELECT*FROM episodes where id like :id")
    suspend fun getEpisodeByIdFromDB(id: Int): EpisodeEntity

    @Query("DELETE FROM episodes")
    suspend fun clearAll()
}