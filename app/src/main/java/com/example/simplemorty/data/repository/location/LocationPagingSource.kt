package com.example.simplemorty.data.repository.location

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.simplemorty.data.models.entity.location.LocationEntity
import com.example.simplemorty.data.models.entity.location.mapToLocationCommonResponse
import com.example.simplemorty.data.models.entity.location.toCachedLocationEntity
import com.example.simplemorty.data.models.entity.location.toLocationEntity
import com.example.simplemorty.data.network.api.location.LocationApi

private const val STARTING_PAGE_INDEX = 1

internal class LocationPagingSource(
    private val locationApi: LocationApi
) : PagingSource<Int, LocationEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationEntity> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = mapToLocationCommonResponse(locationApi.getLocations(pageNumber))

            var nextPageNumber: Int? = null

            if (response.body()?.info?.next != null) {
                val uri = Uri.parse(response.body()?.info?.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            val results = response.body()?.results?.map { it.toCachedLocationEntity() } ?: emptyList()

            LoadResult.Page(
                data = results,
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LocationEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}