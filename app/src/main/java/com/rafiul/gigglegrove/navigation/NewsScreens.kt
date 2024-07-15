package com.rafiul.gigglegrove.navigation

enum class NewsScreens {

    HomeScreen,
    FavoriteScreen;

    companion object {
        fun fromRoute(route: String?): NewsScreens = when (route?.substringBefore("/")) {
            HomeScreen.name -> HomeScreen
            FavoriteScreen.name -> FavoriteScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognised")
        }
    }

}