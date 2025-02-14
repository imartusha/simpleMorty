package com.example.rickandmorty.ui.pages.episodes
import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.log
import com.example.simplemorty.data.models.dto.episode.mapDtoToEpisode
import com.example.simplemorty.data.network.api.episode.EpisodeApi
import com.example.simplemorty.domain.models.Episode


private const val STARTING_PAGE_INDEX = 1

internal class EpisodePagingSource (
    private val episodeApi:EpisodeApi
):PagingSource<Int , Episode>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response =
                episodeApi.getAllEpisodes(
                    pageNumber
                )

            var nextPageNumber: Int? = null

            Log.e("Marta super", "third commit commit")
            if (response.body()?.info?.next != null) {
                val uri = Uri.parse(response.info?.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }
            val results = response.results?.map { mapDtoToEpisode(it) } ?: emptyList()
            LoadResult.Page(
                data = results,
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
        Log.e("Marta super", "123")
    }

    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}

