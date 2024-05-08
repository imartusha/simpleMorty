package com.example.simplemorty.data.database.character

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.simplemorty.data.models.entity.character.FavoriteEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface FavoriteCharacterDao {
    @Query("DELETE FROM favorite_entity")
    suspend fun clearCharacters()

    @Query("SELECT * FROM favorite_entity")
    fun getAllFavoriteCharacters(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteCharacter(character: FavoriteEntity)

    @Delete
    suspend fun deleteFavoriteCharacter(character: FavoriteEntity)

    @Update
    suspend fun updateCharacter(character: FavoriteEntity)

    @Query("UPDATE favorite_entity SET isFavorite=:isFavorite WHERE id=:id")
    suspend fun updateFavoriteState(id: Int, isFavorite: Boolean)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_entity WHERE id = :id)")
    suspend fun isCharacterInFavorites(id: Int): Boolean
}