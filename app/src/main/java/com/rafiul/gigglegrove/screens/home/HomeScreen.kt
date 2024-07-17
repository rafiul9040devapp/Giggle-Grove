package com.rafiul.gigglegrove.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rafiul.gigglegrove.components.ActionButtons
import com.rafiul.gigglegrove.components.CustomErrorText
import com.rafiul.gigglegrove.components.CustomProgressIndicator
import com.rafiul.gigglegrove.components.MakeTheJoke
import com.rafiul.gigglegrove.model.data.JokeEntity
import com.rafiul.gigglegrove.navigation.JokesScreens
import com.rafiul.gigglegrove.utils.HandleApiState
import com.rafiul.gigglegrove.utils.Helper.showSnackBar
import com.rafiul.gigglegrove.utils.JokeMapper.mapToEntity
import kotlinx.coroutines.CoroutineScope


const val TAG = "HomeScreen"


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewmodel: HomeViewModel) {

    val jokeState by viewmodel.responseJokeState.collectAsState()
    var joke by remember { mutableStateOf<JokeEntity?>(null) }

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Jokes App") },
                actions = {
                    IconButton(onClick = { loadingRandomJokes(viewmodel) }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        },
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
                apiState = jokeState,
                onLoading = {
                    CustomProgressIndicator(color = Color.Green)
                },
                onError = { error -> CustomErrorText(text = error) },
                onSuccess = { data ->
                    joke = mapToEntity(data)
                    joke?.let {
                        MakeTheJoke(it)
                    }
                },
                onEmpty = { CustomErrorText(text = "Just Wait For A While....") }
            )

            Spacer(modifier = Modifier.height(32.dp))

            ActionButtons(
                onHomeClick = { loadingRandomJokes(viewmodel) },
                onFavoriteClick = {
                    addingJokesToMyFavoriteList(
                        joke,
                        viewmodel,
                        coroutineScope,
                        snackBarHostState
                    )
                },
                onShareClick = { navigateToFavoriteScreen(navController) }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


private fun navigateToFavoriteScreen(navController: NavController) {
    navController.navigate(JokesScreens.FavoriteScreen.name)
}


private fun loadingRandomJokes(viewmodel: HomeViewModel) = viewmodel.getRandomJokes()


private fun addingJokesToMyFavoriteList(
    joke: JokeEntity?,
    viewmodel: HomeViewModel,
    coroutineScope: CoroutineScope,
    snackBarHostState: SnackbarHostState
) {
    joke?.let {
        viewmodel.addJokesToFavorite(it)
        showSnackBar(coroutineScope, snackBarHostState, title = "Added to favorites")
    }
}






