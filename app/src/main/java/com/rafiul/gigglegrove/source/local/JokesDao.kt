package com.rafiul.gigglegrove.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.rafiul.gigglegrove.model.data.JokeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JokesDao {
    @Query("SELECT * FROM jokes ORDER BY id DESC")
    fun getAllJokes(): Flow<List<JokeEntity>>

    @Upsert
    suspend fun insertAll(jokeEntity: JokeEntity)

    @Delete
    suspend fun deleteJoke(jokeEntity: JokeEntity)
}