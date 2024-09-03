@file:OptIn(ExperimentalMaterial3Api::class)

package com.jorge.rickyandmartyapp.ui.detail_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jorge.rickyandmartyapp.presentation.CharacterDetailContent

@Composable
fun CharacterDetailScreen(characterId: String?, navController: NavController) {
    val viewModel: CharacterDetailViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(characterId) {
        characterId?.let { viewModel.loadCharacterDetail(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Character Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                when (state) {
                    is CharacterDetailState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is CharacterDetailState.Success -> {
                        CharacterDetailContent(character = (state as CharacterDetailState.Success).character)
                    }

                    is CharacterDetailState.Error -> {
                        Text(
                            text = "Error: ${(state as CharacterDetailState.Error).message}",
                            color = Color.Red
                        )
                    }
                }
            }
        }
    )
}