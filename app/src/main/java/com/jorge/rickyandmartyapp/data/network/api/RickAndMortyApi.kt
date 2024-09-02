package com.jorge.rickyandmartyapp.data.network.api

import com.apollographql.apollo3.ApolloClient
import com.jorge.GetCharactersQuery
import com.jorge.rickyandmartyapp.data.network.mapper.toDomain
import com.jorge.rickyandmartyapp.domain.model.CharacterInfo
import com.jorge.rickyandmartyapp.domain.model.CharacterPages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RickAndMortyApi @Inject constructor(
    private val apolloClient: ApolloClient
) {
    fun getCharacters(page: Int): Flow<Pair<List<CharacterInfo>, CharacterPages?>> {
        return apolloClient
            .query(GetCharactersQuery(page))
            .toFlow()
            .map { response ->
                val (characters, pages) = response.data?.characters?.toDomain() ?: Pair(emptyList(), null)
                Pair(characters, pages)
            }
    }
}

