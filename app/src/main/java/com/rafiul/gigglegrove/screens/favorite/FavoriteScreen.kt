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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rafiul.gigglegrove.components.CustomDialogue
import com.rafiul.gigglegrove.components.CustomErrorText
import com.rafiul.gigglegrove.components.CustomProgressIndicator
import com.rafiul.gigglegrove.components.MakeTheJoke
import com.rafiul.gigglegrove.model.data.JokeEntity
import com.rafiul.gigglegrove.utils.HandleApiState
import com.rafiul.gigglegrove.utils.Helper.showSnackBar
import kotlinx.coroutines.CoroutineScope


const val TAG = "FavoriteScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavController, viewmodel: FavoriteViewModel) {
    val favoriteJokeState by viewmodel.favoriteJokes.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Favorite Jokes", textAlign = TextAlign.Center) })
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

    var showDialog by remember { mutableStateOf(false) }
    var selectedJoke by remember { mutableStateOf<JokeEntity?>(null) }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(jokes) { joke ->
            MakeTheJoke(joke = joke, onDeleteClick = {
                showDialog = true
                selectedJoke = joke
            })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    if (showDialog && selectedJoke != null) {
        CustomDialogue(title = "Delete Joke",
            description = "Are you sure you want to delete this joke?",
            confirmText = "Delete",
            onConfirm = {
                selectedJoke?.let { result ->
                    viewmodel.removeJokesFromFavoriteList(result)
                }
                showDialog = false
                showSnackBar(
                    coroutineScope = coroutineScope,
                    snackBarHostState = snackBarHostState,
                    "Joke Is Removed Successfully...."
                )
            },
            onDismiss = {
                showDialog = false
            }
        )
    }
}
