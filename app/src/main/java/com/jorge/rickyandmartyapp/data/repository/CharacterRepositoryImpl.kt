package com.jorge.rickyandmartyapp.data.repository

import com.jorge.rickyandmartyapp.data.network.api.RickAndMortyApi
import com.jorge.rickyandmartyapp.domain.model.CharacterInfo
import com.jorge.rickyandmartyapp.domain.model.CharacterPages
import com.jorge.rickyandmartyapp.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val api: RickAndMortyApi
):CharacterRepository {
    override fun getCharacters(page: Int): Flow<Pair<List<CharacterInfo>, CharacterPages?>> {
        return api.getCharacters(page)
    }
}