package com.jorge.rickyandmartyapp.data.repository

import com.jorge.rickyandmartyapp.domain.model.CharacterDetail
import com.jorge.rickyandmartyapp.data.network.api.DetailCharacterApi
import com.jorge.rickyandmartyapp.domain.model.Location
import com.jorge.rickyandmartyapp.domain.model.Origin
import com.jorge.rickyandmartyapp.domain.repository.CharacterDetailRepository
import javax.inject.Inject

class CharacterDetailRepositoryImpl @Inject constructor(
    private val graphqlClient: DetailCharacterApi
) : CharacterDetailRepository {
    override suspend fun getCharacterDetail(characterId: String): Result<CharacterDetail> {
        return try {
            val response = graphqlClient.queryCharacterDetail(characterId)
            val character = response?.character
            if (character != null) {
                val characterDetail = CharacterDetail(
                    id = character.id,
                    name = character.name,
                    status = character.status,
                    species = character.species,
                    type = character.type ?: "",
                    gender = character.gender,
                    image = character.image,
                    origin = Origin(
                        id = character.origin?.id ?: "",
                        name = character.origin?.name
                    ),
                    location = Location(
                        id = character.location?.id ?: "",
                        name = character.location?.name
                    )
                )
                Result.success(characterDetail)
            } else {
                Result.failure(Exception("Character not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}