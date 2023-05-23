package com.animeproject.data.repository

import com.animeproject.data.remote.AnimeApi
import com.animeproject.data.remote.response.character.CharacterData
import com.animeproject.data.remote.response.character.CharactersResponse
import com.animeproject.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: AnimeApi
) : CharacterRepository {
    override suspend fun searchCharacters(q: String): CharactersResponse =
        api.searchCharacters(q)

    override suspend fun getCharacterById(id: Int): CharacterData =
        api.getCharacterById(id)
}
