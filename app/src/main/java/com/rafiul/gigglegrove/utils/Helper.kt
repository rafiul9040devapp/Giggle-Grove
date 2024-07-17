package com.rafiul.gigglegrove.utils

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object Helper{
     fun showSnackBar(
        coroutineScope: CoroutineScope,
        snackBarHostState: SnackbarHostState,
        title:String,
    ) = coroutineScope.launch {
        snackBarHostState.showSnackbar(title)
    }
}