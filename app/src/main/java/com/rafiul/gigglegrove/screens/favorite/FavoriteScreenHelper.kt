package com.rafiul.gigglegrove.screens.favorite

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rafiul.gigglegrove.components.CustomDialogue
import com.rafiul.gigglegrove.components.MakeTheJoke
import com.rafiul.gigglegrove.model.data.JokeEntity
import com.rafiul.gigglegrove.utils.Helper.showSnackBar
import kotlinx.coroutines.CoroutineScope

class FavoriteScreenHelper {

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun AppBarConfiguration(navController: NavController) {
        val canNavigateBack = navController.previousBackStackEntry != null
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Favorite Jokes", color = Color.Black,
                    fontSize = 32.sp,
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back Arrow",
                            tint = Color.Black
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(Color.White)
        )
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
            CustomDialogue(
                title = "Delete Joke",
                description = "Are you sure you want to delete this joke?",
                confirmText = "Delete",
                onConfirm = {
                    selectedJoke?.let {
                        viewmodel.removeJokesFromFavoriteList(it)
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
}