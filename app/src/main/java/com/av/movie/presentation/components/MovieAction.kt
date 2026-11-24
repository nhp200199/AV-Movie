package com.av.movie.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.av.avmovie.R
import com.av.movie.ui.theme.AVMovieTheme
import com.av.movie.ui.theme.Blue90
import com.av.movie.ui.theme.BrandGradient
import com.av.movie.ui.theme.BrandPrimary
import com.av.movie.ui.theme.White

@Composable
fun MovieAction(
    isSaved: Boolean,
    modifier: Modifier = Modifier,
    onPlayClick: () -> Unit = {},
    onSaveClick: () -> Unit = {}
) {
    val contentColor = MaterialTheme.colorScheme.onBackground
    val outlineColor = MaterialTheme.colorScheme.outline

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Play button expands to fill remaining parent width (Option A)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .height(44.dp)
                .clip(CircleShape)
                .background(brush = BrandGradient)
                .clickable(onClick = onPlayClick)
                .padding(horizontal = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(color = White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = null,
                    tint = Blue90,
                    modifier = Modifier.size(14.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Play",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )
        }

        // Compact Save pill button beside Play
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(44.dp)
                .clip(CircleShape)
                .background(
                    color = if (isSaved) BrandPrimary.copy(alpha = 0.14f) else Color.Transparent
                )
                .border(
                    width = 1.5.dp,
                    color = if (isSaved) BrandPrimary else outlineColor,
                    shape = CircleShape
                )
                .clickable(onClick = onSaveClick)
                .padding(horizontal = 18.dp)
        ) {
            Icon(
                painter = painterResource(
                    id = if (isSaved) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark_outline
                ),
                contentDescription = if (isSaved) "Saved" else "Save",
                tint = if (isSaved) BrandPrimary else contentColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = if (isSaved) "Saved" else "Save",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isSaved) BrandPrimary else contentColor
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieActionPreview() {
    AVMovieTheme(darkTheme = true) {
        MovieAction(
            isSaved = false,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieActionSavedLightPreview() {
    AVMovieTheme(darkTheme = false) {
        MovieAction(
            isSaved = true,
            modifier = Modifier.padding(16.dp)
        )
    }
}
