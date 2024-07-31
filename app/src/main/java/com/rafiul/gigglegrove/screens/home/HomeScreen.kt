package com.rafiul.gigglegrove.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.rafiul.gigglegrove.model.data.JokeEntity
import kotlinx.coroutines.time.delay
import java.time.Duration


const val TAG = "HomeScreen"


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navController: NavController, viewmodel: HomeViewModel,helper: HomeScreenHelper) {

    val jokeState by viewmodel.responseJokeState.collectAsState()
    var joke by remember { mutableStateOf<JokeEntity?>(null) }

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {
            helper.loadingRandomJokes(viewmodel)
            delay(Duration.ofSeconds(15))
        }
    }

    Scaffold(
        topBar = {
            helper.AppBarConfiguration(viewmodel)
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
            joke = helper.handlingTheJokeResponse(jokeState, joke)
            Spacer(modifier = Modifier.height(32.dp))
            helper.HandlingTheActions(viewmodel, joke, coroutineScope, snackBarHostState, navController)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}








