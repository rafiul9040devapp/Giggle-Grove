package com.rafiul.gigglegrove.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rafiul.gigglegrove.model.data.JokeEntity

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

    @Composable
    fun ShowJokeDetails(
        backgroundColor: Color,
        joking: JokeEntity,
        textColor: Color
    ) {
        Surface(
            modifier = Modifier
                .padding(32.dp)
                .wrapContentSize()
                .clip(shape = RoundedCornerShape(topStart = 32.dp, bottomEnd = 32.dp))
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(shape = RoundedCornerShape(topStart = 32.dp, bottomEnd = 32.dp))
                    .shadow(elevation = 10.dp)
                    .background(backgroundColor),
            ) {
                Column(
                    modifier = Modifier
                        .background(backgroundColor),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = joking.joke ?: "N/A",
                        color = textColor,
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(32.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }


}