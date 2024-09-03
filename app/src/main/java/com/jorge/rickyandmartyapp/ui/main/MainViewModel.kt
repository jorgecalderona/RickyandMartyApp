package com.jorge.rickyandmartyapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorge.rickyandmartyapp.domain.model.CharacterInfo
import com.jorge.rickyandmartyapp.domain.model.CharacterPages
import com.jorge.rickyandmartyapp.domain.use_case.getCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCharacterUseCase: getCharacterUseCase
) : ViewModel() {
    private val _charactersState = MutableStateFlow(CharactersState())
    val charactersState: StateFlow<CharactersState> = _charactersState.asStateFlow()

    init {
        loadCharacters(1)
    }

    fun loadCharacters(page: Int) {
        viewModelScope.launch {
            _charactersState.update { it.copy(isLoading = true) }
            getCharacterUseCase(page)
                .catch { exception ->
                    _charactersState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message
                        )
                    }
                }
                .collect { result ->
                    if (result.isSuccess) {
                        val (characters, pages) = result.getOrNull() ?: Pair(emptyList(), null)
                        _charactersState.update { currentState ->
                            currentState.copy(
                                characters = currentState.characters + characters,
                                pages = pages,
                                isLoading = false
                            )
                        }
                    } else {
                        _charactersState.update {
                            it.copy(
                                isLoading = false,
                                error = result.exceptionOrNull()?.message
                            )
                        }
                    }
                }
        }
    }
}


data class CharactersState(
    val characters: List<CharacterInfo> = emptyList(),
    val pages: CharacterPages? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

