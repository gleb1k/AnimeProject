package com.animeproject.data.remote.response.character


import com.google.gson.annotations.SerializedName

data class CharacterData(
    @SerializedName("mal_id")
    val malId: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("images")
    val images: Images,
    @SerializedName("name")
    val name: String,
    @SerializedName("name_kanji")
    val nameKanji: String?,
    @SerializedName("nicknames")
    val nicknames: List<String?>,
    @SerializedName("favorites")
    val favorites: Int?,
    @SerializedName("about")
    val about: String?
)
