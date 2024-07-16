package com.rafiul.gigglegrove.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rafiul.gigglegrove.components.ActionButtons
import com.rafiul.gigglegrove.components.MakeTheJoke
import com.rafiul.gigglegrove.model.data.JokeEntity
import com.rafiul.gigglegrove.navigation.JokesScreens
import com.rafiul.gigglegrove.utils.ApiState
import com.rafiul.gigglegrove.utils.JokeMapper.mapToEntity
import kotlinx.coroutines.launch


const val TAG = "HomeScreen"


@Composable
fun HomeScreen(navController: NavController, viewmodel: HomeViewModel) {

    val jokeState by viewmodel.responseJokeState.collectAsState()
    var joke by remember { mutableStateOf<JokeEntity?>(null) }

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


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
                    CircularProgressIndicator(color = Color.Green)
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
                onFavoriteClick = {
                    joke?.let {
                        viewmodel.addJokesToFavorite(it)
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar("Added to favorites")
                        }
                    }
                },
                onShareClick = { navController.navigate(JokesScreens.FavoriteScreen.name) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
        }
    }
}




