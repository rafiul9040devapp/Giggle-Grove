package com.rafiul.gigglegrove.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafiul.gigglegrove.model.data.JokeEntity
import com.rafiul.gigglegrove.model.response.ResponseJoke
import com.rafiul.gigglegrove.repository.JokeRepository
import com.rafiul.gigglegrove.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: JokeRepository) : ViewModel() {
    private val categories = listOf("Miscellaneous", "Programming", "Dark", "Pun", "Christmas")

    private val _responseJokeState: MutableStateFlow<ApiState<ResponseJoke>> = MutableStateFlow(ApiState.Empty)
    val responseJokeState: StateFlow<ApiState<ResponseJoke>>
        get() = _responseJokeState

    init {
        getRandomJokes()
    }

    fun getRandomJokes() {
        viewModelScope.launch {
            val randomCategory = categories.random()
            repository.getJokesFromRepo(randomCategory).collect { state ->
                _responseJokeState.value = state
            }
        }
    }

    fun addJokesToFavorite(joke: JokeEntity){
        viewModelScope.launch {
            repository.insertJokesToFavoriteList(joke)
        }
    }
}
