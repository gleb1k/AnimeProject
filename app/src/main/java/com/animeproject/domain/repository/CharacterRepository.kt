package com.animeproject.domain.repository

import com.animeproject.data.remote.response.character.CharacterData

interface CharacterRepository {

    suspend fun searchCharacters(
        q: String
    ): List<CharacterData>

    suspend fun getCharacterById(
        id: Int
    ): CharacterData
}
