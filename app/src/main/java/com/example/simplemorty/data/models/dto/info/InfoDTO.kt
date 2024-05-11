package com.example.simplemorty.data.models.dto.info

import com.google.gson.annotations.SerializedName

class InfoDTO(

    @SerializedName("pages")
    val pages: Int? = 0,
    @SerializedName("count")
    val count: Int? = 0,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("prev")
    val prev: String? = null
)