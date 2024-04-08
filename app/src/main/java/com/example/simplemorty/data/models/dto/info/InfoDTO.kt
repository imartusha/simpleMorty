package com.example.simplemorty.data.models.dto.info

import com.google.gson.annotations.SerializedName

class InfoDTO(

    @SerializedName("count")
    val count: Int,

    @SerializedName("next")
    val next: String,

    @SerializedName("pages")
    val pages: Int,

    @SerializedName("prev")
    val prev: Any?
)