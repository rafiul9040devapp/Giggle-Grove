package com.rafiul.gigglegrove.repository

import android.database.sqlite.SQLiteException
import com.rafiul.gigglegrove.model.data.JokeEntity
import com.rafiul.gigglegrove.model.response.ResponseJoke
import com.rafiul.gigglegrove.source.local.JokesDao
import com.rafiul.gigglegrove.source.remote.JokeApi
import com.rafiul.gigglegrove.utils.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JokeRepositoryImpl @Inject constructor(
    private val api: JokeApi,
    private val jokesDao: JokesDao
) : JokeRepository {
    override suspend fun getJokesFromRepo(category: String): Flow<ApiState<ResponseJoke>> =
        flow<ApiState<ResponseJoke>> {
            emit(ApiState.Empty)
            try {
                emit(ApiState.Loading)
                val response = api.getJokesFromApi(category)
                if (response.isSuccessful) {
                    response.body()?.let { responseJoke ->
                        emit(ApiState.Success(responseJoke))
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
        }.retry(3) { e -> e is UnknownHostException || e is IOException }.flowOn(Dispatchers.IO)

    override suspend fun getAllFavoriteJokesFromLocal(): Flow<ApiState<List<JokeEntity>>> =
        flow<ApiState<List<JokeEntity>>> {
            emit(ApiState.Empty)
            try {
                emit(ApiState.Loading)
                val jokeFlow = jokesDao.getAllJokes()

                jokeFlow.catch { e -> emit(ApiState.Error("Unable To Load Data: {${e.message}}")) }
                    .retry(3) { e -> e is SQLiteException }
                    .collect { jokeEntityList -> emit(ApiState.Success(jokeEntityList)) }

            } catch (e: Exception) {
                emit(ApiState.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)


    override suspend fun insertJokesToFavoriteList(jokeEntity: JokeEntity) = jokesDao.insertAll(jokeEntity)

    override suspend fun deleteJokesFromFavoriteList(jokeEntity: JokeEntity) = jokesDao.deleteJoke(jokeEntity)
}
