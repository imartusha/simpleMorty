package com.example.simplemorty.data.models.response

import com.example.simplemorty.data.models.dto.info.InfoDTO
import com.example.simplemorty.domain.models.CharacterProfile
import com.example.simplemorty.domain.models.Episode
import com.example.simplemorty.domain.models.Info
import com.example.simplemorty.domain.models.Location
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.Response

sealed class ApiResponse<out T> {
    data class Progress<T>(var loading: Boolean) : ApiResponse<T>()
    data class Success<T>(var data: T) : ApiResponse<T>()
    data class Failure<T>(val e: Throwable,var data: Response<ResponseBody>?) : ApiResponse<T>()

    companion object {
        fun <T> loading(isLoading: Boolean): ApiResponse<T> = Progress(isLoading)
        fun <T> success(data: T): ApiResponse<T> = Success(data)
        fun <T> failure(e: Throwable,data : Response<ResponseBody>?): ApiResponse<T> = Failure(e,data)
    }
}
data class CharactersResponse(
    @SerializedName("info")
    val info: Info?,
    @SerializedName("results")
    val results: List<CharacterProfile>? = emptyList()
)
data class EpisodeResponse(
    @SerializedName("info")
    val info: Info? ,
    @SerializedName("results")
    val results: List<Episode>? = emptyList()
)

data class LocationResponse(
    @SerializedName("info")
    val infoDTO: InfoDTO? = InfoDTO(),
    @SerializedName("results")
    val results: List<Location>? = emptyList()
)