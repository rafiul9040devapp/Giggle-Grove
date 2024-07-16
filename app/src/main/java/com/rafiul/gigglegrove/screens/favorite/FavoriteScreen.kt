package com.rafiul.gigglegrove.screens.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController


const val TAG = "FavoriteScreen"

@Composable
fun FavoriteScreen(navController: NavController, viewmodel: FavoriteViewModel) {
    val favoriteJokeState by viewmodel.favoriteJokes.collectAsState()


}