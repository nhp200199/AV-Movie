package com.av.movie.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.av.avmovie.R
import com.av.movie.test.MODEL_MOVIE_Gladiator_II
import com.av.movie.test.MODEL_POPULAR_MOVIES
import com.av.movie.test.getFullPosterPath
import com.av.movie.ui.theme.LightGrey30
import com.av.movie.ui.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val voteAverage: Double,
    val isFavorite: Boolean
)

@Composable
fun MyHomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGrey30)
    ) {
        MovieCarousel(
            modifier = Modifier.height(500.dp)
                .fillMaxWidth(),
            movies = MODEL_POPULAR_MOVIES
        )
        Spacer(Modifier.height(16.dp))
        Text("Pager", modifier = Modifier.fillMaxWidth().weight(2.5f),
            style = TextStyle(fontSize = 56.sp),)
    }
}

@Composable
fun MovieCarousel(
    modifier: Modifier = Modifier,
    movies: List<Movie>
) {
    val listState = rememberLazyListState()
    val pagerState = rememberPagerState(pageCount = { movies.size })

    val pagerIsDragged by pagerState.interactionSource.collectIsDraggedAsState()

    val pageInteractionSource = remember { MutableInteractionSource() }
    val pageIsPressed by pageInteractionSource.collectIsPressedAsState()

    // Stop auto-advancing when pager is dragged or one of the pages is pressed
    val autoAdvance = !pagerIsDragged && !pageIsPressed

    if (autoAdvance) {
        LaunchedEffect(pagerState, pageInteractionSource) {
            while (true) {
                delay(2000)
                val nextPage = (pagerState.currentPage + 1) % movies.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.settledPage }.collect { page ->
            listState.animateScrollToItem(page)
        }
    }


    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(7.5f)
        ) { page ->
            PopularMovie(
                movie = movies[page],
            )
        }

        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 32.dp, horizontal = 8.dp),
        ) {
            items(count = movies.size) { idx ->
                AsyncImage(
                    model = getFullPosterPath(movies[idx].posterPath),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .width(125.dp)
                        .height(50.dp)
                        .alpha(if (pagerState.currentPage == idx) 1f else 0.3f)
                        .clickable { coroutineScope.launch {
                            pagerState.animateScrollToPage(idx)
                        } },
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_launcher_background)
                )
            }
        }
    }
}

@Composable
fun PopularMovie(modifier: Modifier = Modifier, movie: Movie) {
    Box(
        modifier = modifier
//            .background(Color.White)
    ) {
        AsyncImage(
            model = getFullPosterPath(movie.posterPath),
            contentDescription = movie.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.ic_launcher_background)
        )

        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxHeight()
                .padding(start = 8.dp)
        ) {
            MovieInfo(movie.voteAverage, movie.title)
            Spacer(Modifier.height(16.dp))
            MovieAction()
        }
    }
}

@Composable
fun MovieInfo(avgRating: Double, title: String) {
    Column {
        Row {
            Text("IMdb", color = White, fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))
            Text(
                BigDecimal(avgRating)
                    .setScale(1, RoundingMode.HALF_UP)
                    .toDouble()
                    .toString(),
                color = White,
                fontSize = 14.sp
            )
        }
        Text(
            title,
            color = White,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun MovieAction() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(modifier = Modifier.width(150.dp), onClick = {}) {
            Text("Watch Now")
        }
        Spacer(modifier = Modifier.width(24.dp))
        Button(
            onClick = {},
            modifier = Modifier
                .size(40.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favorite"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyHomeScreenPreview() {
    MyHomeScreen()
}

@Preview(showBackground = true)
@Composable
fun MovieActionPreview() {
    MovieAction()
}

@Preview()
@Composable
fun MovieInfoPreview() {
    MovieInfo(
        MODEL_MOVIE_Gladiator_II.voteAverage,
        MODEL_MOVIE_Gladiator_II.title
    )
}

@Preview()
@Composable
fun PopularMoviePreview() {
    PopularMovie(
        modifier = Modifier.fillMaxSize(),
        movie = MODEL_MOVIE_Gladiator_II
    )
}