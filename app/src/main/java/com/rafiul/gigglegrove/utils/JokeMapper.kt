package com.rafiul.gigglegrove.utils

import com.rafiul.gigglegrove.model.data.JokeEntity
import com.rafiul.gigglegrove.model.response.ResponseJoke

class JokeMapper {
    fun mapToEntity(responseJoke: ResponseJoke): JokeEntity {
        return JokeEntity(
            id = responseJoke.id,
            category = responseJoke.category,
            joke = responseJoke.joke
        )
    }
}
