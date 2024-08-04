package com.rafiul.gigglegrove.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.google.gson.Gson
import com.rafiul.gigglegrove.components.getCategoryColors
import com.rafiul.gigglegrove.model.data.JokeEntity

const val TAG = "DetailsScreen"

@Composable
fun DetailsScreen(navController: NavController, helper: DetailsScreenHelper, joke: String) {

    val decodedJokeJson = java.net.URLDecoder.decode(joke, "UTF-8")
    val joking = Gson().fromJson(decodedJokeJson, JokeEntity::class.java)

    val (backgroundColor, textColor) = getCategoryColors(joking.category ?: "")

    Scaffold(
        topBar = {
            helper.AppBarConfiguration(navController, joking.category ?: "N/A")
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(.15f))
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
}