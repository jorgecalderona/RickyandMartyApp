package com.jorge.rickyandmartyapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
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
                    fetchCharacters = viewModel::loadCharacters,
                    onCharacterClick = { id ->
                        println("Clicked character ID: " + id)
                    }
                )
            }
        }
    }
}

@Composable
fun Content(
    charactersState: CharactersState,
    fetchCharacters: (Int) -> Unit,
    onCharacterClick: (String) -> Unit
) {
    val listState = rememberLazyListState() // Recordar el estado de la lista

    // Detectar cuando el usuario llega al final de la lista
    LaunchedEffect(listState) {
        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.collect { lastVisibleItemIndex ->
            val totalItems = charactersState.characters.size
            val nextPage = charactersState.pages?.nextPage

            Log.d("Content", "Last visible item index: $lastVisibleItemIndex")
            Log.d("Content", "Total items: $totalItems")
            Log.d("Content", "Next page: $nextPage")

            if (lastVisibleItemIndex != null && lastVisibleItemIndex >= totalItems - 1 && nextPage != null) {
                Log.d("Content", "Fetching next page: $nextPage")
                fetchCharacters(nextPage)
            } else {
                Log.d("Content", "Not fetching next page. Conditions not met.")
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            items(charactersState.characters) { character ->
                CharacterItem(
                    character = character,
                    onClick = { id -> onCharacterClick(id) }
                )
            }
        }

        // Mostrar el loading cuando se esté cargando
        if (charactersState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center) // Centramos el loading
            )
        }
    }
}



@Composable
fun CharacterItem(
    character: CharacterInfo,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // Asegura que la tarjeta ocupe todo el ancho disponible
            .padding(vertical = 4.dp)
            .clickable { onClick(character.id ?: "") }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth() // Asegura que el contenido dentro del Row también ocupe todo el ancho disponible
                .padding(8.dp)
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp) // Ajusta el tamaño de la imagen según sea necesario
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = character.name ?: "No Name",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth() // Asegura que el texto ocupe todo el ancho disponible
                    .align(Alignment.CenterVertically), // Centra el texto verticalmente dentro del Row
                textAlign = TextAlign.Center // Centra el texto horizontalmente dentro del espacio del Text
            )
        }
    }
}


