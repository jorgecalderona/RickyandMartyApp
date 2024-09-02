package com.jorge.rickyandmartyapp.domain.repository

import com.jorge.rickyandmartyapp.domain.model.CharacterInfo
import com.jorge.rickyandmartyapp.domain.model.CharacterPages
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(page: Int): Flow<Pair<List<CharacterInfo>, CharacterPages?>>
}
