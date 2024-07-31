package com.rafiul.gigglegrove.di

import com.rafiul.gigglegrove.repository.JokeRepository
import com.rafiul.gigglegrove.repository.JokeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
   abstract fun bindPhotoRepository(jokeRepositoryImpl: JokeRepositoryImpl): JokeRepository
}