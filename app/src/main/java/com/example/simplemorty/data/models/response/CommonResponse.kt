package com.example.simplemorty.data.models.response

import com.example.simplemorty.domain.models.Info
import com.google.gson.annotations.SerializedName

internal class CommonResponse<T : Result>(

    @SerializedName("info")
    val info: Info,

    @SerializedName("results")
    val results: List<T>
)

internal interface Result


