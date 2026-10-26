package com.av.movie.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.av.avmovie.R
import com.av.movie.core.utils.getFullBackdropPath
import com.av.movie.core.utils.getFullPosterPath
import com.av.movie.dataTest.GLADIATOR_II
import com.av.movie.domain.model.Movie

@Composable
fun MovieItem(
    movie: Movie,
    modifier: Modifier = Modifier,
    isBackdrop: Boolean = false,
    onNavigateToMovieDetail: (id: Int) -> Unit = {}
) {
    Column(
        modifier = modifier
            .clickable { onNavigateToMovieDetail(movie.id) },
    ) {
        Box {
            AsyncImage(
                model = if (isBackdrop) getFullBackdropPath(movie.backdropPath)
                else getFullPosterPath(movie.posterPath),
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = movie.title,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 8.dp, top = 8.dp)
            ) {
                RatingChip(rating = movie.voteAverage)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        //only show text for horizontal images movie
        if (isBackdrop)
            Text(fontSize = 14.sp, color = Color.White, text = movie.title)
    }
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    MovieItem(movie = GLADIATOR_II, modifier = Modifier.width(110.dp))
}