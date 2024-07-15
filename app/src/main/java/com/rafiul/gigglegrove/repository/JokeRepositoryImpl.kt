package com.rafiul.gigglegrove.repository

import com.rafiul.gigglegrove.model.data.JokeEntity
import com.rafiul.gigglegrove.model.response.ResponseJoke
import com.rafiul.gigglegrove.source.local.JokesDao
import com.rafiul.gigglegrove.source.remote.JokeApi
import com.rafiul.gigglegrove.utils.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.UnknownHostException
import javax.inject.Inject

class JokeRepositoryImpl @Inject constructor(private val api: JokeApi,private val jokesDao: JokesDao) : JokeRepository {
    override suspend fun getJokesFromRepo(category: String): Flow<ApiState<ResponseJoke>>  = flow<ApiState<ResponseJoke>> {
        emit(ApiState.Empty)
        try {
            emit(ApiState.Loading)
            val response = api.getJokesFromApi(category)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(ApiState.Success(it))
                } ?: emit(ApiState.Error("Empty Response"))
            } else {
                emit(ApiState.Error("Response not successful: ${response.message()}"))
            }
        } catch (e: Exception) {
            if (e is UnknownHostException) {
                emit(ApiState.Error("Check Your Internet Connection"))
            } else {
                emit(ApiState.Error(e.toString()))
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun insertJokesToFavoriteList(jokeEntity: JokeEntity) {

    }



}
