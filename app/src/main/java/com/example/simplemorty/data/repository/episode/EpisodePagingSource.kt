package com.example.simplemorty.data.repository.episode

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.simplemorty.data.models.entity.episode.EpisodeEntity
import com.example.simplemorty.data.models.entity.episode.mapToEpisodeResponse
import com.example.simplemorty.data.models.entity.episode.toCachedEpisodeEntity
import com.example.simplemorty.data.network.api.episode.EpisodeApi


private const val STARTING_PAGE_INDEX = 1

internal class EpisodePagingSource(
    private val episodeApi: EpisodeApi
) : PagingSource<Int, EpisodeEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeEntity> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = mapToEpisodeResponse(episodeApi.getEpisodes(pageNumber))

            var nextPageNumber: Int? = null

            if (response.body()?.info?.next != null) {
                val uri = Uri.parse(response.body()?.info?.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            val results = response.body()?.results?.map { it.toCachedEpisodeEntity() } ?: emptyList()

            LoadResult.Page(
                data = results,
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, EpisodeEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

