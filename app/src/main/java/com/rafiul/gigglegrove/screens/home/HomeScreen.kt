package com.rafiul.gigglegrove.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Loop
import androidx.compose.material.icons.filled.Moving
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rafiul.gigglegrove.model.data.JokeEntity
import com.rafiul.gigglegrove.navigation.JokesScreens
import com.rafiul.gigglegrove.utils.ApiState
import com.rafiul.gigglegrove.utils.JokeMapper.mapToEntity


const val TAG = "HomeScreen"


@Composable
fun HomeScreen(navController: NavController, viewmodel: HomeViewModel) {

    val jokeState by viewmodel.responseJokeState.collectAsState()
    var joke by remember { mutableStateOf<JokeEntity?>(null) }


    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = jokeState) {
                is ApiState.Loading -> {
                    CircularProgressIndicator(color = Color.Blue)
                }

                is ApiState.Error -> {
                    Text(
                        text = state.exception,
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        textDecoration = TextDecoration.Underline,
                        color = Color.Red
                    )
                }

                is ApiState.Success -> {
                    joke = mapToEntity(state.data)
                    joke?.let {
                        MakeTheJoke(it)
                    }
                }

                is ApiState.Empty -> {
                    Text(
                        text = "Just Wait For A While....",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        textDecoration = TextDecoration.Underline,
                        color = Color.Red
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            ActionButtons(
                onHomeClick = { viewmodel.getRandomJokes() },
                onFavoriteClick = { joke?.let { viewmodel.addJokesToFavorite(it) } },
                onShareClick = { navController.navigate(JokesScreens.FavoriteScreen.name) }
            )

        }

    }


}


@Composable
fun MakeTheJoke(joke: JokeEntity) {
    val (backgroundColor, textColor) = getCategoryColors(joke.category ?: "")
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = joke.joke ?: "N/A",
                color = textColor,
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Bottom) {
                Text(
                    text = joke.category ?: "N/A",
                    color = textColor,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

    }
}

@Composable
fun getCategoryColors(category: String): Pair<Color, Color> {
    return when (category) {
        "Miscellaneous" -> Pair(Color.LightGray, Color.Black)
        "Programming" -> Pair(Color.Blue, Color.White)
        "Dark" -> Pair(Color.Black, Color.White)
        "Pun" -> Pair(Color.Yellow, Color.Black)
        "Christmas" -> Pair(Color.Cyan, Color.White)
        else -> Pair(Color.White, Color.Black)
    }
}

@Composable
fun ActionButtons(
    onHomeClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onShareClick: () -> Unit,
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onHomeClick) {
                Icon(Icons.Default.Loop, contentDescription = "Load Jokes")
            }

            IconButton(onClick = onShareClick) {
                Icon(Icons.Default.Moving, contentDescription = "Favorite List")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onFavoriteClick) {
                Icon(Icons.Default.Favorite, contentDescription = "Add To Favorite")
            }
        }
    }
}
