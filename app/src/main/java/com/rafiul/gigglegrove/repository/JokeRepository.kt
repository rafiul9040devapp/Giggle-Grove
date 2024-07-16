package com.rafiul.gigglegrove.repository

import com.rafiul.gigglegrove.model.data.JokeEntity
import com.rafiul.gigglegrove.model.response.ResponseJoke
import com.rafiul.gigglegrove.utils.ApiState
import kotlinx.coroutines.flow.Flow

interface JokeRepository {
    suspend fun getJokesFromRepo(category:String): Flow<ApiState<ResponseJoke>>

    suspend fun insertJokesToFavoriteList(jokeEntity: JokeEntity)

    suspend fun getAllFavoriteJokesFromLocal(): Flow<ApiState<List<JokeEntity>>>

    suspend fun deleteJokesFromFavoriteList(jokeEntity: JokeEntity)
}