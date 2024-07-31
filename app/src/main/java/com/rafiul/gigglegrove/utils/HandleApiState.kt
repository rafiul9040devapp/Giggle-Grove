package com.rafiul.gigglegrove.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.rafiul.gigglegrove.components.CustomErrorText
import com.rafiul.gigglegrove.components.CustomProgressIndicator

@Composable
fun <T> HandleApiState(
    apiState: ApiState<T>,
    onLoading: @Composable (() -> Unit)? = null,
    onError: @Composable (String) -> Unit,
    onSuccess: @Composable (T) -> Unit,
    onEmpty: @Composable (() -> Unit)? = null
) {
    when (apiState) {
        is ApiState.Loading ->{
           if (onLoading == null){
               CustomProgressIndicator(color = Color.Green)
           }
        }
        is ApiState.Error -> onError(apiState.exception)
        is ApiState.Success -> onSuccess(apiState.data)
        is ApiState.Empty -> {
            if (onEmpty == null) {
                CustomErrorText(text = "Just Wait For A While....")
            }
        }
    }
}