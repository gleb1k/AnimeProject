package com.animeproject.data.remote.response.character


import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("data")
    val data: List<CharacterData?>,
    @SerializedName("pagination")
    val pagination: Pagination
)
