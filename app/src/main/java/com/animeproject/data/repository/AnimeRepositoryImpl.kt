package com.animeproject.data.repository

import com.animeproject.data.remote.AnimeApi
import com.animeproject.data.remote.response.anime.AnimeData
import com.animeproject.domain.repository.AnimeRepository
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val api: AnimeApi
) : AnimeRepository {
    override suspend fun searchAnimes(q: String): List<AnimeData> =
        api.searchAnimes(q).data

    override suspend fun getAnimeById(id: Int): AnimeData = api.getAnimeById(id).data

}
