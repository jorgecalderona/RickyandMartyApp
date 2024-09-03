package com.jorge.rickyandmartyapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jorge.rickyandmartyapp.domain.model.CharacterDetail

@Composable
fun CharacterDetailContent(character: CharacterDetail) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = character.image,
            contentDescription = null,
            modifier = Modifier
                .size(270.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(19.dp))
        Text(text = "Name: ${character.name}", style = MaterialTheme.typography.titleLarge)
        Text(text = "Status: ${character.status}", style = MaterialTheme.typography.titleMedium)
        Text(text = "Species: ${character.species}", style = MaterialTheme.typography.titleMedium)
        Text(text = "Type: ${character.type}", style = MaterialTheme.typography.titleMedium)
        Text(text = "Gender: ${character.gender}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Origin: ${character.origin?.name}", style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "Location: ${character.location?.name}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}