package com.rafiul.gigglegrove.di

import android.content.Context
import com.rafiul.gigglegrove.source.local.JokeDatabase
import com.rafiul.gigglegrove.source.local.JokesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): JokeDatabase = JokeDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideDao(jokeDatabase: JokeDatabase): JokesDao = jokeDatabase.getJokesDao()
}