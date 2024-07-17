package com.rafiul.gigglegrove.screens.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rafiul.gigglegrove.components.CustomErrorText
import com.rafiul.gigglegrove.components.CustomProgressIndicator
import com.rafiul.gigglegrove.components.MakeTheJoke
import com.rafiul.gigglegrove.model.data.JokeEntity
import com.rafiul.gigglegrove.utils.HandleApiState


const val TAG = "FavoriteScreen"

@Composable
fun FavoriteScreen(navController: NavController, viewmodel: FavoriteViewModel) {
    val favoriteJokeState by viewmodel.favoriteJokes.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HandleApiState(
                apiState = favoriteJokeState,
                onLoading = { CustomProgressIndicator(color = Color.Green) },
                onError = { error ->
                    CustomErrorText(text = error)
                },
                onSuccess = { data ->
                   JokeList(jokes = data, viewmodel = viewmodel)
                },
                onEmpty = {
                    CustomErrorText(text = "Just Wait For A While....")
                }
            )
        }
    }
}

@Composable
fun JokeList(jokes: List<JokeEntity>,viewmodel: FavoriteViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(jokes) { joke ->
            MakeTheJoke(joke = joke, onDeleteClick = {
                viewmodel.removeJokesFromFavoriteList(joke)
            })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}