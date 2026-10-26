package com.av.movie.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.av.movie.dataTest.GLADIATOR_II
import com.av.movie.ui.theme.White

@Composable
fun MovieInfo(
    avgRating: Double,
    title: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row {
            RatingChip(rating = avgRating)
        }
        Text(
            title,
            color = White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
fun MovieInfoPreview() {
    MovieInfo(
        GLADIATOR_II.voteAverage,
        GLADIATOR_II.title
    )
}