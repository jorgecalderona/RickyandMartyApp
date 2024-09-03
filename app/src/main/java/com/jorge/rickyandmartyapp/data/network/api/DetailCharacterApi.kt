package com.jorge.rickyandmartyapp.data.network.api

import com.apollographql.apollo3.ApolloClient
import com.jorge.CharacterDetailQuery
import javax.inject.Inject

class DetailCharacterApi @Inject constructor(
    private val apolloClient: ApolloClient
) {
    suspend fun queryCharacterDetail(characterId: String): CharacterDetailQuery.Data? {
        val response = apolloClient.query(CharacterDetailQuery(id = characterId)).execute()
        return response.data
    }
}