package com.jorge.rickyandmartyapp.domain.repository

import com.jorge.rickyandmartyapp.domain.model.CharacterDetail

interface CharacterDetailRepository {
    suspend fun getCharacterDetail(characterId: String): Result<CharacterDetail>
}
