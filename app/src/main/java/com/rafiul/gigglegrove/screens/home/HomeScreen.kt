package com.rafiul.gigglegrove.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rafiul.gigglegrove.components.JokeCard
import com.rafiul.gigglegrove.components.PullToRefreshLazyColumn
import com.rafiul.gigglegrove.utils.ApiState


const val TAG = "HomeScreen"

@Composable
fun HomeScreen(navController: NavController, viewmodel: HomeViewModel) {

    val jokeState by viewmodel.responseJokeState.collectAsState()
    val isLoading = jokeState is ApiState.Loading

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PullToRefreshLazyColumn(
            items = listOf(jokeState),
            content = { state ->
                when (state) {
                    is ApiState.Success -> {
                        JokeCard(
                            joke = state.data,
                            onFavoriteClick = { /* Handle add to favorites */ }
                        )
                    }

                    is ApiState.Error -> {
                        Text(text = "Error: ${state.exception}")
                    }

                    is ApiState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is ApiState.Empty -> {
                        Text(text = "No data")
                    }
                }
            },
            isRefreshing = isLoading,
            onRefresh = { viewmodel.getRandomJokes() },
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        )
    }
}
