package com.rafiul.gigglegrove.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Loop
import androidx.compose.material.icons.filled.Moving
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ActionButtons(
    onHomeClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onNavigateClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButtonWithCircle(
                onClick = onHomeClick,
                icon = Icons.Default.Loop,
                contentDescription = "Load Jokes",
                backgroundColor = Color(0xFF03A9F4),
            )

            IconButtonWithCircle(
                onClick = onNavigateClick,
                icon = Icons.Default.Moving,
                contentDescription = "Favorite List",
                backgroundColor = Color(0xFFF8A121),
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButtonWithCircle(
                onClick = onFavoriteClick,
                icon = Icons.Default.Favorite,
                contentDescription = "Add To Favorite",
                backgroundColor = Color(0xFFE91E63),
            )
        }
    }
}

@Composable
private fun IconButtonWithCircle(
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    backgroundColor: Color,
    iconColor: Color = Color.White
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .padding(16.dp)
            .clickable(onClick = onClick)
    ) {
        Icon(
            icon,
            contentDescription = contentDescription,
            tint = iconColor,
            modifier = Modifier.size(32.dp)
        )
    }
}
