package com.example.simplemorty.data.repository.location

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.simplemorty.data.network.api.location.LocationApi
import com.example.simplemorty.domain.models.Location

private const val STARTING_PAGE_INDEX = 1
internal class LocationPagingSource (
    private val api: LocationApi
): PagingSource<Int, Location>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Location> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response =
                api.getLocations(
                    pageNumber

                )

            var nextPageNumber: Int? = null

            if (response.body()?.infoDTO?.next != null) {
                val uri = Uri.parse(response.body()?.infoDTO?.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = response.body()?.results!!,
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Location>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}