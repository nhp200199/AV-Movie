package com.av.movie.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@Composable
fun GradientText(
    text: String,
    modifier: Modifier = Modifier,
    gradient: Brush = brush,
    textStyle: TextStyle = TextStyle()
) {
    Text(
        text = text,
        modifier = modifier,
        style = textStyle.copy(brush = gradient),
    )
}

@Composable
fun GradientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    gradient: Brush = brush,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        modifier = modifier
            .background(
                gradient,
                shape = ButtonDefaults.shape
            )
        ,
        onClick = onClick,
        colors = colors
            .copy(containerColor = Color.Transparent)
    ) {
        content()
    }
}

@Preview
@Composable
private fun GradientButtonPreview() {
    GradientButton(
        onClick = {},
    ) {
        Text(
            text = "Hello",
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
private fun GradientTextPreview() {
    GradientText(
        text = "Hello",
    )
}