package com.av.movie.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.av.movie.ui.theme.Blue90
import com.av.movie.ui.theme.Cyan90
import com.av.movie.ui.theme.LightGrey30

@Composable
fun MovieAction(
    isFavorite: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val colors = listOf(
            Cyan90,
            Blue90
        )

        Button(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(colors),
                    shape = ButtonDefaults.shape
                )
                .width(150.dp)
                .height(ButtonDefaults.MinHeight),
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
            )
        ) {
            Text("Watch Now")
        }
        Spacer(modifier = Modifier.width(24.dp))
        Button(
            onClick = {},
            modifier = Modifier
                .size(40.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LightGrey30
            )
        ) {
            val brush = Brush.linearGradient(
                listOf(
                    Cyan90,
                    Blue90
                )
            )

            Icon(
                contentDescription =
                    if (isFavorite) "Favorite" else "Not Favorite",
                modifier = Modifier
                    .graphicsLayer(alpha = 0.99f)
                    .drawWithCache {
                        onDrawWithContent {
                            drawContent()
                            drawRect(brush, blendMode = BlendMode.SrcAtop)
                        }
                    },
                imageVector =
                    if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieActionPreview() {
    MovieAction(isFavorite = true)
}