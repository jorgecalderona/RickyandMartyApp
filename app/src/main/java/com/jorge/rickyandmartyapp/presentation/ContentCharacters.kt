package com.jorge.rickyandmartyapp.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jorge.rickyandmartyapp.ui.main.CharactersState

@Composable
fun ContentCharacters(
    charactersState: CharactersState,
    fetchCharacters: (Int) -> Unit,
    onCharacterClick: (String) -> Unit
) {
    val characters = charactersState.characters
    val nextPage = charactersState.pages?.nextPage ?: 0

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            charactersState.isLoading && characters.isEmpty() -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            charactersState.error != null -> {
                Text(
                    text = "Error: ${charactersState.error}",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    itemsIndexed(characters) { index, character ->
                        CharacterItem(
                            character = character,
                            onClick = { id -> onCharacterClick(id) }
                        )
                        if (index == characters.size - 1 && !charactersState.isLoading) {
                            LaunchedEffect(Unit) {
                                fetchCharacters(nextPage)
                            }
                        }
                    }
                    if (charactersState.isLoading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}




