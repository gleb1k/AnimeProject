package com.animeproject.data.remote.response.anime


import com.google.gson.annotations.SerializedName

data class Trailer(
    @SerializedName("youtube_id")
    val youtubeId: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("embed_url")
    val embedUrl: String?
)
