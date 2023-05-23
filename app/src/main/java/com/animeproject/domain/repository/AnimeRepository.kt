package com.animeproject.domain.repository

import com.animeproject.data.remote.response.anime.AnimeData
import com.animeproject.data.remote.response.anime.AnimesResponse

interface AnimeRepository {

    suspend fun searchAnimes(
        q: String
    ): AnimesResponse

    suspend fun getAnimeById(
        id: Int
    ): AnimeData
}
