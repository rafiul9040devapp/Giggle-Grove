package com.rafiul.gigglegrove.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.rafiul.gigglegrove.components.ActionButtons
import com.rafiul.gigglegrove.components.CustomErrorText
import com.rafiul.gigglegrove.components.MakeTheJoke
import com.rafiul.gigglegrove.model.data.JokeEntity
import com.rafiul.gigglegrove.model.response.ResponseJoke
import com.rafiul.gigglegrove.navigation.JokesScreens
import com.rafiul.gigglegrove.utils.ApiState
import com.rafiul.gigglegrove.utils.HandleApiState
import com.rafiul.gigglegrove.utils.Helper.showSnackBar
import com.rafiul.gigglegrove.utils.JokeMapper.mapToEntity
import kotlinx.coroutines.CoroutineScope

class HomeScreenHelper {
    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun AppBarConfiguration(viewmodel: HomeViewModel) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Giggle Grove", color = Color.Black,
                    fontSize = 32.sp,
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            actions = {
                IconButton(onClick = { loadingRandomJokes(viewmodel) }) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Refresh",
                        tint = Color.Cyan,
                        modifier = Modifier.size(32.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(Color.White)
        )
    }

    @Composable
    fun handlingTheJokeResponse(
        jokeState: ApiState<ResponseJoke>,
        joke: JokeEntity?,
        navController: NavController
    ): JokeEntity? {
        var entity = joke
        HandleApiState(
            apiState = jokeState,
            onError = { error -> CustomErrorText(text = error) },
            onSuccess = { data ->
                entity = mapToEntity(data)
                entity?.let {
                    Surface(
                        modifier = Modifier
                            .clickable {
                                if (it.joke != null) {
                                    navigateToDetailsScreen(navController = navController, joke = it)
                                }
                            }
                            .padding(16.dp)
                            .clip(shape = RoundedCornerShape(topStart = 32.dp, bottomEnd = 32.dp))
                    ) {
                        MakeTheJoke(it)
                    }
                }
            },
        )
        return entity
    }

    @Composable
    fun HandlingTheActions(
        viewmodel: HomeViewModel,
        joke: JokeEntity?,
        coroutineScope: CoroutineScope,
        snackBarHostState: SnackbarHostState,
        navController: NavController
    ) {
        ActionButtons(
            onHomeClick = { loadingRandomJokes(viewmodel) },
            onFavoriteClick = {
                addingJokesToMyFavoriteList(
                    joke,
                    viewmodel,
                    coroutineScope,
                    snackBarHostState
                )
            },
            onNavigateClick = { navigateToFavoriteScreen(navController) }
        )
    }


    fun loadingRandomJokes(viewmodel: HomeViewModel) = viewmodel.getRandomJokes()

    private fun navigateToFavoriteScreen(navController: NavController) =
        navController.navigate(JokesScreens.FavoriteScreen.name)

    private fun addingJokesToMyFavoriteList(
        joke: JokeEntity?,
        viewmodel: HomeViewModel,
        coroutineScope: CoroutineScope,
        snackBarHostState: SnackbarHostState
    ) {
        joke?.let {
            if (joke.joke != null) {
                viewmodel.addJokesToFavorite(it)
                showSnackBar(coroutineScope, snackBarHostState, title = "Added to favorites")
            } else {
                showSnackBar(coroutineScope, snackBarHostState, title = "Empty Joke")
            }
        }
    }

    private fun navigateToDetailsScreen(navController: NavController, joke: JokeEntity) {
        val jokeJson = Gson().toJson(joke)
        val encodedJokeJson = java.net.URLEncoder.encode(jokeJson, "UTF-8")
        navController.navigate("${JokesScreens.DetailsScreen.name}/$encodedJokeJson")
    }
}



