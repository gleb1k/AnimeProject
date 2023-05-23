package com.animeproject.domain.repository

import com.animeproject.data.remote.response.character.CharacterData
import com.animeproject.data.remote.response.character.CharactersResponse

interface CharacterRepository {

    suspend fun searchCharacters(
        q: String
    ): CharactersResponse

    suspend fun getCharacterById(
        id: Int
    ): CharacterData
}
