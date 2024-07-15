package com.rafiul.gigglegrove.source.remote

import com.rafiul.gigglegrove.model.response.ResponseJoke
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JokeApi {
    @GET("joke/{type}")
    suspend fun getJokesFromApi(@Path("type") type: String): Response<ResponseJoke>
}
