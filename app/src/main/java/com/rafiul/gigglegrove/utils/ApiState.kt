package com.rafiul.gigglegrove.utils

sealed class ApiState<out T> {
    data class Success<out T>(val data: T) : ApiState<T>()
    data class Error(val exception: String) : ApiState<Nothing>()
    data object Loading : ApiState<Nothing>()
    data object Empty : ApiState<Nothing>()
}