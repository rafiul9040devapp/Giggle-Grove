package com.rafiul.gigglegrove.screens.details

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

class DetailsScreenHelper {

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun AppBarConfiguration(navController: NavController,jokeType:String) {
        val canNavigateBack = navController.previousBackStackEntry != null
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "$jokeType Joke", color = Color.Black,
                    fontSize = 32.sp,
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back Arrow",
                            tint = Color.Black
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(Color.White)
        )
    }


}