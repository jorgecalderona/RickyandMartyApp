package com.jorge.rickyandmartyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jorge.rickyandmartyapp.presentation.ContentCharacters
import com.jorge.rickyandmartyapp.ui.detail_screen.CharacterDetailScreen
import com.jorge.rickyandmartyapp.ui.main.CharactersState

@Composable
fun NavHostContainer(
    charactersState: CharactersState,
    fetchCharacters: (Int) -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "character_list") {
        composable("character_list") {
            ContentCharacters(
                charactersState = charactersState,
                fetchCharacters = fetchCharacters,
                onCharacterClick = { id ->
                    navController.navigate("character_detail/$id")
                }
            )
        }
        composable("character_detail/{characterId}") { backStackEntry ->
            val characterId = backStackEntry.arguments?.getString("characterId")
            CharacterDetailScreen(characterId.toString(), navController)
        }
    }
}
