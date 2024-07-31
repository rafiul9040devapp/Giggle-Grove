package com.rafiul.gigglegrove.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rafiul.gigglegrove.screens.favorite.FavoriteScreen
import com.rafiul.gigglegrove.screens.favorite.FavoriteScreenHelper
import com.rafiul.gigglegrove.screens.favorite.FavoriteViewModel
import com.rafiul.gigglegrove.screens.home.HomeScreen
import com.rafiul.gigglegrove.screens.home.HomeScreenHelper
import com.rafiul.gigglegrove.screens.home.HomeViewModel


@Composable
fun JokesNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = JokesScreens.HomeScreen.name
    ) {

        composable(route = JokesScreens.HomeScreen.name) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            val homeScreenHelper = HomeScreenHelper()
            HomeScreen(navController = navController,viewmodel = homeViewModel,helper = homeScreenHelper)
        }

        composable(route = JokesScreens.FavoriteScreen.name) {
            val favoriteViewModel = hiltViewModel<FavoriteViewModel>()
            val favoriteScreenHelper = FavoriteScreenHelper()
            FavoriteScreen(navController = navController,viewmodel = favoriteViewModel,helper = favoriteScreenHelper)
        }

    }
}