package com.rafiul.gigglegrove.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rafiul.gigglegrove.model.data.JokeEntity

@Composable
fun MakeTheJoke(joke: JokeEntity, onDeleteClick: (() -> Unit)? = null) {
    val (backgroundColor, textColor) = getCategoryColors(joke.category ?: "")
    if (joke.joke == null){
        JokeNotAvailable()
    } else{
        JokeCard(backgroundColor, textColor, joke, onDeleteClick)
    }
}

@Composable
private fun JokeNotAvailable() {
    Box(
        modifier = Modifier
            .fillMaxWidth().fillMaxHeight(.2f)
            .padding(16.dp)
            .background(color = Color.Red),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Not Available",
            color = Color.White,
            fontSize = 32.sp,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun JokeCard(
    backgroundColor: Color,
    textColor: Color,
    joke: JokeEntity,
    onDeleteClick: (() -> Unit)?
) {

    Card(
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(backgroundColor),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(backgroundColor)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = joke.joke ?: "N/A",
                color = textColor,
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = getTextAlign(joke.joke)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = getCategoryDisplayText(joke),
                    color = textColor,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(8.dp)
                )
                if (onDeleteClick != null) {
                    Spacer(modifier = Modifier.width(4.dp))
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            imageVector = Icons.Default.DeleteForever,
                            contentDescription = "Delete",
                            tint = Color.Red,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }



}

@Composable
fun getCategoryColors(category: String): Pair<Color, Color> {
    return when (category) {
        "Miscellaneous" -> Pair(Color.LightGray, Color.Black)
        "Programming" -> Pair(Color.Magenta, Color.White)
        "Dark" -> Pair(Color.Black, Color.White)
        "Pun" -> Pair(Color.Yellow, Color.Black)
        "Christmas" -> Pair(Color.Cyan, Color.Black)
        else -> Pair(Color.White, Color.Black)
    }
}

private fun getTextAlign(joke: String?): TextAlign {
    return if (joke == null) TextAlign.Center else TextAlign.Start
}

private fun getCategoryDisplayText(joke: JokeEntity): String {
    return if (joke.joke != null) {
        joke.category ?: "N/A"
    } else {
        ""
    }
}
