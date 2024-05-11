package com.example.simplemorty.data.repository.location

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.simplemorty.data.database.character.DataBase
import com.example.simplemorty.data.models.entity.location.RemoteKeysLocation
import com.example.simplemorty.data.models.entity.location.LocationEntity
import com.example.simplemorty.data.models.entity.location.mapToCachedLocationEntity
import com.example.simplemorty.data.network.api.location.LocationApi

@OptIn(ExperimentalPagingApi::class)
internal class LocationRemoteMediator(
    private val locationApi: LocationApi,
    private val database: DataBase
) : RemoteMediator<Int, LocationEntity>() {

    private val remoteKeysLocationDao = database.remoteKeysLocationDao
    private val cacheDao = database.cachedLocationDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocationEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val response = locationApi.getLocations(page)
            val locations = response.body()?.results ?: emptyList()
            val sortedLocations = locations.sortedBy { it.id }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysLocationDao.clearRemoteKeys()
                    database.cachedLocationDao.clearLocations()
                }
            }
            val prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1)
            val nextKey = if ((LoadState.Loading.endOfPaginationReached)) null else page.plus(1)

            Log.e("MyTag", "Loading page $page, loadType=${loadType.name}")

            val cachedLocations = mapToCachedLocationEntity(locations)
            cacheDao.insertAll(cachedLocations)

            val remoteKeys = sortedLocations.map {
                RemoteKeysLocation(
                    repoId = it.id,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
            remoteKeysLocationDao.insertAll(remoteKeys)

            return MediatorResult.Success(endOfPaginationReached = locations.isEmpty())
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, LocationEntity>): RemoteKeysLocation? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                database.remoteKeysLocationDao.remoteKeysRepoId(it.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, LocationEntity>): RemoteKeysLocation? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                database.remoteKeysLocationDao.remoteKeysRepoId(it.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, LocationEntity>): RemoteKeysLocation? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                database.remoteKeysLocationDao.remoteKeysRepoId(repoId)
            }
        }
    }
}
