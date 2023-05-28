package com.animeproject.data.remote

import com.animeproject.data.remote.response.anime.AnimeResponse
import com.animeproject.data.remote.response.anime.AnimesResponse
import com.animeproject.data.remote.response.character.CharacterResponse
import com.animeproject.data.remote.response.character.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// https://api.jikan.moe/v4/
interface AnimeApi {

    @GET("anime")
    suspend fun searchAnimes(
        @Query("q") q: String
    ): AnimesResponse

    @GET("anime/{id}")
    suspend fun getAnimeById(
        @Path("id") id: Int
    ): AnimeResponse

    @GET("characters")
    suspend fun searchCharacters(
        @Query("q") q: String
    ): CharactersResponse

    @GET("characters/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): CharacterResponse

}
