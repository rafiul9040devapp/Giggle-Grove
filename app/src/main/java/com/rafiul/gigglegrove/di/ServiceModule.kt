package com.rafiul.gigglegrove.di


import com.rafiul.gigglegrove.source.remote.JokeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class ServiceModule {
    @Singleton
    @Provides
    fun providesJokeApi(retrofit: Retrofit.Builder): JokeApi {
        return retrofit.build().create(JokeApi::class.java)
    }

}