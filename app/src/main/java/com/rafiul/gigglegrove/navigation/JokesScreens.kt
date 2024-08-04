package com.rafiul.gigglegrove.navigation

enum class JokesScreens {

    HomeScreen,
    FavoriteScreen,
    DetailsScreen;


    companion object {
        fun fromRoute(route: String?): JokesScreens = when (route?.substringBefore("/")) {
            HomeScreen.name -> HomeScreen
            FavoriteScreen.name -> FavoriteScreen
            DetailsScreen.name -> DetailsScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognised")
        }
    }
}