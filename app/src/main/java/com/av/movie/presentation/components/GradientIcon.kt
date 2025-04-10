package com.av.movie.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import com.av.movie.ui.theme.Blue90
import com.av.movie.ui.theme.Cyan90

private val brush = Brush.linearGradient(listOf(
    Cyan90,
    Blue90
))

@Composable
fun GradientIcon(
    icon: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier,
    gradient: Brush = brush
) {
    Icon(
        contentDescription = contentDescription,
        modifier = modifier
            .graphicsLayer(alpha = 0.99f)
            .drawWithCache {
                onDrawWithContent {
                    drawContent()
                    drawRect(gradient, blendMode = BlendMode.SrcAtop)
                }
            },
        imageVector = icon,
    )
}