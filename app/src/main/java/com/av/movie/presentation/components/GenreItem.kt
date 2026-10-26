package com.av.movie.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.av.avmovie.R
import com.av.movie.core.utils.conditional
import com.av.movie.dataTest.MODEL_GENRE_ACTION
import com.av.movie.domain.model.Genre
import com.av.movie.ui.theme.Blue90
import com.av.movie.ui.theme.LightGrey30

@Composable
fun GenreItem(
    genre: Genre,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onGenreSelected: (Genre) -> Unit = {}
) {
    Box(
        modifier = modifier
            .paint(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentScale = ContentScale.FillWidth,
                alpha = if (isSelected) 1f else 0.9f
            )
            .conditional(
                condition = isSelected,
                ifTrue = { border(width = 1.dp, color = Blue90) }
            )
            .clickable {
                onGenreSelected(genre)
            }
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.9f)
        ) {
            Text(
                text = genre.name,
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(
                        color = LightGrey30.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            )
        }

        if (isSelected) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "Checked",
                tint = Blue90,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp, end = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GenreItemPreview() {
    GenreItem(
        genre = MODEL_GENRE_ACTION,
        modifier = Modifier
            .width(150.dp)
    )
}