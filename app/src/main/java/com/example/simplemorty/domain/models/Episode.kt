package com.example.simplemorty.domain.models

import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("air_date")
    val airDate: String,
    val characters: List<String>,
    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
)
