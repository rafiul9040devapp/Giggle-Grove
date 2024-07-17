package com.rafiul.gigglegrove.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomProgressIndicator(
    color: Color = MaterialTheme.colorScheme.primary,
    size: Dp = 32.dp,
    strokeWidth: Dp = 4.dp
) {
    Canvas(
        modifier = Modifier.size(size)
    ) {
        val canvasSize = size.toPx()
        val stroke = strokeWidth.toPx()
        drawRoundRect(
            color = color.copy(alpha = 0.3f),
            topLeft = Offset(stroke / 2, stroke / 2),
            size = Size(canvasSize - stroke, canvasSize - stroke),
            cornerRadius = CornerRadius(x = canvasSize / 2, y = canvasSize / 2),
            style = Stroke(stroke)
        )
        drawArc(
            color = color,
            startAngle = 0f,
            sweepAngle = 360f * 0.75f,
            useCenter = false,
            topLeft = Offset(stroke / 2, stroke / 2),
            size = Size(canvasSize - stroke, canvasSize - stroke),
            style = Stroke(stroke)
        )
    }
}
