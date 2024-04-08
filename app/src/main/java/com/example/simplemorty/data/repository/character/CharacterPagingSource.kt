package com.example.simplemorty.data.repository.character

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.simplemorty.data.models.dto.character.mapDtoToCharacterProfile
import com.example.simplemorty.data.network.api.character.CharacterApi
import com.example.simplemorty.domain.models.CharacterProfile

internal class CharacterPagingSource(
    private val characterApi: CharacterApi
) : PagingSource<Int, CharacterProfile>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterProfile> {
        val pageNumber = params.key ?: 1
        Log.d("MyPagingSource", "Loading page $pageNumber")

        return try {
            val response = characterApi.getAllCharacters(pageNumber)

            var nextPageNumber: Int? = null

            if (response.info?.next != null) {
                val uri = Uri.parse(response.info?.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            val results = response.results?.map { mapDtoToCharacterProfile(it) } ?: emptyList()

            LoadResult.Page(
                data = results,
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterProfile>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}