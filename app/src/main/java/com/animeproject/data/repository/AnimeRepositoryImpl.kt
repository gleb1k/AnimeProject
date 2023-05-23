package com.animeproject.data.repository

import com.animeproject.data.remote.AnimeApi
import com.animeproject.data.remote.response.anime.AnimeData
import com.animeproject.data.remote.response.anime.AnimesResponse
import com.animeproject.domain.repository.AnimeRepository
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val api : AnimeApi
) : AnimeRepository {
    override suspend fun searchAnimes(q: String): AnimesResponse =
        api.searchAnimes(q)

    override suspend fun getAnimeById(id: Int): AnimeData =
        api.getAnimeById(id)
}
