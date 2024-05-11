package com.example.simplemorty.data.database.location

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simplemorty.data.models.entity.location.LocationEntity

@Dao
interface CachedLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locations: List<LocationEntity>)

    @Query("SELECT * FROM locations")
    fun getLocations(): PagingSource<Int, LocationEntity>

    @Query("DELETE FROM locations")
    suspend fun clearLocations()

    @Query("SELECT * FROM locations WHERE id = :id")
    suspend fun getLocationById(id: Int): LocationEntity?

    @Query("SELECT * FROM locations WHERE id IN (:locationIdsList)")
    fun getMultipleLocation(locationIdsList: List<String>): List<LocationEntity>
}
