package com.rafiul.gigglegrove.screens.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavController
import com.rafiul.gigglegrove.components.CustomErrorText
import com.rafiul.gigglegrove.utils.HandleApiState


const val TAG = "FavoriteScreen"


@Composable
fun FavoriteScreen(navController: NavController, viewmodel: FavoriteViewModel,helper: FavoriteScreenHelper) {
    val favoriteJokeState by viewmodel.favoriteJokes.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            helper.AppBarConfiguration(navController)
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
                apiState = favoriteJokeState,
                onError = { error -> CustomErrorText(text = error) },
                onSuccess = { data ->
                    helper.JokeList(
                        data,
                        viewmodel,
                        coroutineScope,
                        snackBarHostState,
                        navController
                    )
                },
            )
        }
    }
}


