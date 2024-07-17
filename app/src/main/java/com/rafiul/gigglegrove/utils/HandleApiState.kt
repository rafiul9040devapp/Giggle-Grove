package com.rafiul.gigglegrove.utils

import androidx.compose.runtime.Composable

@Composable
fun <T> HandleApiState(
    apiState: ApiState<T>,
    onLoading: @Composable () -> Unit,
    onError: @Composable (String) -> Unit,
    onSuccess: @Composable (T) -> Unit,
    onEmpty: @Composable () -> Unit
) {
    when (apiState) {
        is ApiState.Loading -> onLoading()
        is ApiState.Error -> onError(apiState.exception)
        is ApiState.Success -> onSuccess(apiState.data)
        is ApiState.Empty -> onEmpty()
    }
}