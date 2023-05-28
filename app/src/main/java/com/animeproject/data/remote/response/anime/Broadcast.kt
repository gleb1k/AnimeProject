package com.animeproject.data.remote.response.anime

import com.google.gson.annotations.SerializedName

data class Broadcast(
    @SerializedName("day")
    val day: String?,
    @SerializedName("time")
    val time: String?,
    @SerializedName("timezone")
    val timezone: String?,
    @SerializedName("string")
    val string: String?
)
