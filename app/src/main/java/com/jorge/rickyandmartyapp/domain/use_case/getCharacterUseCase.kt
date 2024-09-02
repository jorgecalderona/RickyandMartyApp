package com.jorge.rickyandmartyapp.domain.use_case

import com.jorge.rickyandmartyapp.domain.model.CharacterInfo
import com.jorge.rickyandmartyapp.domain.model.CharacterPages
import com.jorge.rickyandmartyapp.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class getCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke (pages: Int): Flow<Result<Pair<List<CharacterInfo>, CharacterPages?>>> {
        return repository
            .getCharacters(pages)
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }
}