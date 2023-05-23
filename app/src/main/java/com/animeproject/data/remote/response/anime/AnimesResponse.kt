package com.animeproject.data.remote.response.anime


import com.google.gson.annotations.SerializedName

data class AnimesResponse(
    @SerializedName("data")
    val data: List<AnimeData?>,
    @SerializedName("pagination")
    val pagination: Pagination
)
