package com.example.simplemorty.data.repository.character

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.simplemorty.data.models.entity.character.cach.CharacterEntity
import com.example.simplemorty.data.models.entity.character.cach.mapToCharacterProfileResponse
import com.example.simplemorty.data.network.api.character.CharacterApi
import com.example.simplemorty.domain.models.toCachedCharacterEntity

private const val STARTING_PAGE_INDEX = 1

internal class CharacterPagingSource(
    private val characterApi: CharacterApi
) : PagingSource<Int, CharacterEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterEntity> {
        val pageNumber = params.key ?: STARTING_PAGE_INDEX
        Log.d("MyPagingSource", "Loading page $pageNumber")

        return try {
            val response = mapToCharacterProfileResponse(characterApi.getCharacters(pageNumber))

            var nextPageNumber: Int? = null

            if (response.body()?.info?.next != null) {
                val uri = Uri.parse(response.body()?.info?.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            val results = response.body()?.results?.map { it.toCachedCharacterEntity() } ?: emptyList()

            LoadResult.Page(
                data = results,
                prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}