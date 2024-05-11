package com.example.simplemorty.data.repository.episode

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.simplemorty.data.database.character.DataBase
import com.example.simplemorty.data.models.entity.character.RemoteKeysEntity
import com.example.simplemorty.data.models.entity.episode.EpisodeEntity
import com.example.simplemorty.data.models.entity.episode.mapToCachedEpisodeEntity
import com.example.simplemorty.data.network.api.episode.EpisodeApi

@OptIn(ExperimentalPagingApi::class)
internal class EpisodeRemoteMediator(
    private val episodeApi: EpisodeApi,
    private val database: DataBase
) : RemoteMediator<Int, EpisodeEntity>() {

    private val remoteKeysDao = database.remoteKeysDao
    private val cacheDao = database.cachedEpisodeDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EpisodeEntity>
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
            val response = episodeApi.getEpisodes(page)
            val episodes = response.body()?.results ?: emptyList()
            val sortedEpisodes = episodes.sortedBy { it.id }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao.clearRemoteKeys()
                    database.cachedEpisodeDao.clearEpisodes()
                }
            }
            val prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1)
            val nextKey = if ((LoadState.Loading.endOfPaginationReached)) null else page.plus(1)

            val cachedEpisodes = mapToCachedEpisodeEntity(episodes)
            cacheDao.insertAll(cachedEpisodes)

            val remoteKeys = sortedEpisodes.map {
                RemoteKeysEntity(
                    repoId = it.id,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
            remoteKeysDao.insertAll(remoteKeys)

            return MediatorResult.Success(endOfPaginationReached = episodes.isEmpty())
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, EpisodeEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                database.remoteKeysDao.remoteKeysRepoId(it.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, EpisodeEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                database.remoteKeysDao.remoteKeysRepoId(it.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, EpisodeEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                database.remoteKeysDao.remoteKeysRepoId(repoId)
            }
        }
    }
}