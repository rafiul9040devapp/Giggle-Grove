package com.rafiul.gigglegrove.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafiul.gigglegrove.model.data.JokeEntity
import com.rafiul.gigglegrove.repository.JokeRepository
import com.rafiul.gigglegrove.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: JokeRepository) : ViewModel() {

    private val _favoriteJokes: MutableStateFlow<ApiState<List<JokeEntity>>> = MutableStateFlow(ApiState.Empty)
    val favoriteJokes:StateFlow<ApiState<List<JokeEntity>>>
        get() = _favoriteJokes

    init {
        getAllFavoriteJokes()
    }

    private fun getAllFavoriteJokes() {
        viewModelScope.launch {
            repository.getAllFavoriteJokesFromLocal().collect{ state->
                _favoriteJokes.value = state
            }
        }
    }

    fun removeJokesFromFavoriteList(joke: JokeEntity){
        viewModelScope.launch {
            repository.deleteJokesFromFavoriteList(joke)
        }
    }
}