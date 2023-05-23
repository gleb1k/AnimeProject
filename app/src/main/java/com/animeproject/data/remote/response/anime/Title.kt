package com.animeproject.data.remote.response.anime


import com.google.gson.annotations.SerializedName

data class Title(
    @SerializedName("type")
    val type: String?,
    @SerializedName("title")
    val title: String?
)
