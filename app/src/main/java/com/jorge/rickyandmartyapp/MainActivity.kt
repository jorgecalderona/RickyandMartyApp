package com.jorge.rickyandmartyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.jorge.rickyandmartyapp.domain.model.CharacterInfo
import com.jorge.rickyandmartyapp.theme.RickAndMortyApiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModels<MainViewModel>()
        setContent {
            RickAndMortyApiTheme {
                val charactersState by viewModel.charactersState.collectAsStateWithLifecycle()
                Content(
                    charactersState = charactersState,
                    fetchCharacters = viewModel::loadCharacters
                )
            }
        }
    }
}

@Composable
fun Content(
    charactersState: CharactersState,
    fetchCharacters: (Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        if (charactersState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (charactersState.error != null) {
            Text(text = "Error: ${charactersState.error}", color = Color.Red, modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn {
                items(charactersState.characters) { character ->
                    CharacterItem(character)
                }
            }

            charactersState.pages?.nextPage?.let { nextPage ->
                Button(onClick = { fetchCharacters(nextPage) }) {
                    Text(text = "Load more")
                }
            }
        }
    }
}

@Composable
fun CharacterItem(character: CharacterInfo) {
    Card(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = character.image,
                contentDescription = null,
                modifier = Modifier.size(60.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = character.name ?: "No Name", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
