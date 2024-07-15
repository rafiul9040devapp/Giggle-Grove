package com.rafiul.gigglegrove.di

import com.rafiul.gigglegrove.source.remote.JokeApi
import com.rafiul.gigglegrove.repository.JokeRepository
import com.rafiul.gigglegrove.repository.JokeRepositoryImpl
import com.rafiul.gigglegrove.source.local.JokesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun providePhotoRepository(api: JokeApi,jokesDao: JokesDao): JokeRepository = JokeRepositoryImpl(api,jokesDao)
}