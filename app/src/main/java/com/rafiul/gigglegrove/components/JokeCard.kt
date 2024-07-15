package com.rafiul.gigglegrove.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rafiul.gigglegrove.model.response.ResponseJoke

@Composable
fun JokeCard(
    joke: ResponseJoke,
    onFavoriteClick: () -> Unit
) {
    val backgroundColor = when (joke.category) {
        "Miscellaneous" -> Color.LightGray
        "Programming" -> Color.Cyan
        "Dark" -> Color.DarkGray
        "Pun" -> Color.Yellow
        "Christmas" -> Color.Green
        else -> Color.White
    }

    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().background(backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = joke.joke ?: "No joke found",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Button(onClick = onFavoriteClick) {
            Text(text = "Add to Favorites")
        }
    }
}
