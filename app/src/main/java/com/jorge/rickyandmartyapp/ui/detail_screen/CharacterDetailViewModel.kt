package com.jorge.rickyandmartyapp.ui.detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jorge.rickyandmartyapp.domain.model.CharacterDetail
import com.jorge.rickyandmartyapp.domain.use_case.GetCharacterDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterDetailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CharacterDetailState>(CharacterDetailState.Loading)
    val state: StateFlow<CharacterDetailState> = _state.asStateFlow()

    fun loadCharacterDetail(characterId: String) {
        viewModelScope.launch {
            _state.value = CharacterDetailState.Loading
            try {
                val result = getCharacterUseCase(characterId)
                _state.value = if (result.isSuccess) {
                    CharacterDetailState.Success(result.getOrNull()!!)
                } else {
                    CharacterDetailState.Error(
                        result.exceptionOrNull()?.message ?: "An unknown error occurred"
                    )
                }
            } catch (e: Exception) {
                _state.value = CharacterDetailState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}

sealed class CharacterDetailState {
    object Loading : CharacterDetailState()
    data class Success(val character: CharacterDetail) : CharacterDetailState()
    data class Error(val message: String) : CharacterDetailState()
}

