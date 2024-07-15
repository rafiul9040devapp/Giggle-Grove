package com.rafiul.gigglegrove.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokes")
data class JokeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val category: String?,
    val joke: String?
)
