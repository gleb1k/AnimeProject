package com.animeproject.domain.repository

import com.animeproject.data.remote.response.anime.AnimeData

interface AnimeRepository {

    suspend fun searchAnimes(
        q: String
    ): List<AnimeData>

    suspend fun getAnimeById(
        id: Int
    ): AnimeData
}
