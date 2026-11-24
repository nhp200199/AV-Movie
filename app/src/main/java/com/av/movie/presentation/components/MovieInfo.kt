package com.av.movie.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.av.movie.core.utils.formatDate
import com.av.movie.dataTest.GLADIATOR_II
import com.av.movie.ui.theme.AVMovieTheme
import com.av.movie.ui.theme.BrandPrimary

@Composable
fun MovieInfo(
    avgRating: Double,
    title: String,
    modifier: Modifier = Modifier,
    releaseDate: String = ""
) {
    val releaseYear = formatDate(
        pattern = "yyyy",
        date = releaseDate
    )

    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Rating",
                tint = BrandPrimary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = formatMovieRating(avgRating),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = BrandPrimary
            )
            if (releaseYear.isNotEmpty()) {
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = releaseYear,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieInfoPreview() {
    AVMovieTheme(darkTheme = true) {
        MovieInfo(
            avgRating = GLADIATOR_II.voteAverage,
            title = GLADIATOR_II.title,
            releaseDate = GLADIATOR_II.releaseDate
        )
    }
}
