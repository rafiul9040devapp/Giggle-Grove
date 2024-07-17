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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


const val TAG = "FavoriteScreen"

@Composable
fun FavoriteScreen(navController: NavController, viewmodel: FavoriteViewModel) {
    val favoriteJokeState by viewmodel.favoriteJokes.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPadding ->
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
                onError = { error -> CustomErrorText(text = error) },
                onSuccess = { data ->
                    JokeList(
                        data,
                        viewmodel,
                        coroutineScope,
                        snackBarHostState
                    )
                },
                onEmpty = { CustomErrorText(text = "Just Wait For A While....") }
            )
        }
    }
}

@Composable
fun JokeList(
    jokes: List<JokeEntity>,
    viewmodel: FavoriteViewModel,
    coroutineScope: CoroutineScope,
    snackBarHostState: SnackbarHostState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(jokes) { joke ->
            MakeTheJoke(joke = joke, onDeleteClick = {
                viewmodel.removeJokesFromFavoriteList(joke)
                coroutineScope.launch {
                 snackBarHostState.showSnackbar("Joke is removed successfully")
                }
            })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}