package com.animeproject.data.remote.response.character


import com.google.gson.annotations.SerializedName

data class Items(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("per_page")
    val perPage: Int?
)
