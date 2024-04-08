package com.example.simplemorty.data.database.character

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simplemorty.data.models.entity.character.CharacterEntity

import com.example.simplemorty.domain.models.CharacterProfile

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(characterEntity: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): List<CharacterEntity>

//    @Query("SELECT * FROM characters")
//    fun pagingSource(): PagingSource<Int, CharacterProfile>

    @Query("SELECT*FROM characters where id like :id")
    suspend fun getCharacterByIdFromDB(id: Int): CharacterEntity

    @Query("DELETE FROM characters")
    suspend fun clearAll()
}