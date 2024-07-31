package com.rafiul.gigglegrove.navigation

enum class JokesScreens {

    HomeScreen,
    FavoriteScreen;

    companion object {
        fun fromRoute(route: String?): JokesScreens = when (route?.substringBefore("/")) {
            HomeScreen.name -> HomeScreen
            FavoriteScreen.name -> FavoriteScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognised")
        }
    }
}