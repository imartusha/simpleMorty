package com.example.simplemorty.data.database.character

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simplemorty.data.models.entity.character.cach.CharacterEntity

@Dao
interface CachedCharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity>)

    @Query("SELECT * FROM cached_characters")
    fun getCharacters(): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM cached_characters")
    suspend fun clearCharacters()

    @Query("SELECT * FROM cached_characters WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity?

    @Query("SELECT * FROM cached_characters WHERE id IN (:characterIdsList)")
    fun getCharactersByIds(characterIdsList: List<String>): List<CharacterEntity>
}