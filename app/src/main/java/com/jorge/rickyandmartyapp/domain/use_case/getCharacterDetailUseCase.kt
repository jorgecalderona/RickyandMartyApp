package com.jorge.rickyandmartyapp.domain.use_case

import com.jorge.rickyandmartyapp.domain.model.CharacterDetail
import com.jorge.rickyandmartyapp.domain.repository.CharacterDetailRepository
import javax.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(
    private val repository: CharacterDetailRepository
) {
    suspend operator fun invoke(characterId: String): Result<CharacterDetail> {
        return repository.getCharacterDetail(characterId)
    }
}


