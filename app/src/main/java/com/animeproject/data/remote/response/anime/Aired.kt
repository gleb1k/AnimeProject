package com.animeproject.data.remote.response.anime


import com.google.gson.annotations.SerializedName

data class Aired(
    @SerializedName("from")
    val from: String?,
    @SerializedName("to")
    val to: String?,
    @SerializedName("prop")
    val prop: Prop?
)
