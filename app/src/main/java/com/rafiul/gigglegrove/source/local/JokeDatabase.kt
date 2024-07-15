package com.rafiul.gigglegrove.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rafiul.gigglegrove.model.data.JokeEntity

private const val DATABASE_NAME = "joke_db"

@Database(entities = [JokeEntity::class], version = 1)
abstract class JokeDatabase : RoomDatabase() {
    abstract fun getJokesDao(): JokesDao

    companion object {
        private var instance: JokeDatabase? = null
        @Synchronized
        fun getInstance(context: Context): JokeDatabase{
            return if (instance == null){
                instance = buildDatabase(context)
                instance as JokeDatabase
            }else{
                instance as JokeDatabase
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            JokeDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}