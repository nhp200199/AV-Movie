package com.av.movie.presentation.screen.home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.av.avmovie.R
import com.av.movie.dataTest.ALL_GENRES
import com.av.movie.dataTest.GLADIATOR_II
import com.av.movie.dataTest.MODEL_POPULAR_MOVIES
import com.av.movie.dataTest.getFullBackdropPath
import com.av.movie.dataTest.getFullPosterPath
import com.av.movie.domain.model.Movie
import com.av.movie.ui.theme.Blue90
import com.av.movie.ui.theme.Cyan90
import com.av.movie.ui.theme.LightGrey30
import com.av.movie.ui.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

data class Video(
    val id: String,
    val name: String,
    val size: Int,
    val key: String,
    val site: String,
    val type: String,
    val official: Boolean,
    val publishedAt: String,
)

data class Genre(
    val id: Int,
    val name: String
)

@Composable
fun MyHomeScreen(
    onNavigateToCategoryDetail: (category: String) -> Unit,
    onNavigateToMovieDetail: (id: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            MovieCarousel(
                modifier = Modifier
                    .fillMaxWidth(),
                movies = MODEL_POPULAR_MOVIES,
                onNavigateToMovieDetail = onNavigateToMovieDetail
            )
        }

        item {
            Category(
                name = "Popular",
                movies = MODEL_POPULAR_MOVIES,
                modifier = Modifier.padding(8.dp),
                onNavigateToCategoryDetail = onNavigateToCategoryDetail,
                onNavigateToMovieDetail = onNavigateToMovieDetail
            )
        }

        item {
            Genres(
                genres = ALL_GENRES,
                modifier = Modifier
                    .padding(8.dp)
            )
        }

        item {
            MovieCategory(
                name = "Now on TV",
                movies = MODEL_POPULAR_MOVIES,
                modifier = Modifier.padding(8.dp),
                onNavigateToCategoryDetail = {},
                onNavigateToMovieDetail = {}
            )
        }
    }
}

@Composable
fun Genres(
    genres: List<Genre>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            fontSize = 16.sp,
            color = Color.White,
            text = "Categories",
            fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(16.dp))

        LazyHorizontalGrid(
            rows = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(225.dp)
        ) {
            items(
                count = genres.size,
                key = { genres[it].id }
            ) {
                Text(
                    text = genres[it].name,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .width(150.dp)
                        .height(150.dp)
                        .background(
                            color = LightGrey30.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .wrapContentHeight(),
                )
            }
        }
    }
}

@Composable
fun MovieCarousel(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    onNavigateToMovieDetail: (id: Int) -> Unit
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
        ) { page ->
            PopularMovie(
                movie = movies[page],
                onNavigateToMovieDetail = onNavigateToMovieDetail
            )
        }

        LazyRow(
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 8.dp),
        ) {
            items(
                count = movies.size,
                key = { movies[it].id }
            ) { idx ->
                AsyncImage(
                    model = getFullPosterPath(movies[idx].posterPath),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .width(125.dp)
                        .height(50.dp)
                        .alpha(if (pagerState.currentPage == idx) 1f else 0.3f)
                        .clickable {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(idx)
                            }
                        },
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_launcher_background)
                )
            }
        }
    }
}

@Composable
fun PopularMovie(
    modifier: Modifier = Modifier,
    movie: Movie,
    onNavigateToMovieDetail: (id: Int) -> Unit
) {
    Box(
        modifier = modifier
            .clickable { onNavigateToMovieDetail(movie.id) }
    ) {
        AsyncImage(
            model = getFullPosterPath(movie.posterPath),
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = size.height / 3,
                        endY = size.height
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Multiply)
                    }
                },
            contentScale = ContentScale.FillWidth,
            error = painterResource(id = R.drawable.ic_launcher_background),
            placeholder = painterResource(id = R.drawable.ic_launcher_background)
        )

        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 8.dp)
        ) {
            MovieInfo(movie.voteAverage, movie.title)
            Spacer(Modifier.height(16.dp))
            MovieAction(movie.isFavorite)
        }
    }
}

@Composable
fun MovieInfo(avgRating: Double, title: String) {
    Column {
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

@Composable
fun MovieAction(isFavorite: Boolean) {
    Row(
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
            val brush = Brush.linearGradient(listOf(
                Cyan90,
                Blue90
            ))

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

@Composable
fun Category(
    name: String,
    movies: List<Movie>,
    modifier: Modifier = Modifier,
    onNavigateToCategoryDetail: (category: String) -> Unit,
    onNavigateToMovieDetail: (id: Int) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(fontSize = 16.sp, color = Color.White, text = name, fontWeight = FontWeight.SemiBold)
            TextButton(
                onClick = { onNavigateToCategoryDetail("Popular Movies") },
                colors = ButtonDefaults.textButtonColors(

                )
            ) {
                val brush = Brush.linearGradient(listOf(
                    Cyan90,
                    Blue90
                ))

                Text(text = "See all",
                    style = TextStyle(
                        brush = brush
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(
                movies.size,
                key = { movies[it].id }
            ) {
                MovieItem(
                    movie = movies[it],
                    modifier = Modifier.width(110.dp),
                    onNavigateToMovieDetail = onNavigateToMovieDetail
                )
            }
        }
    }
}

@Composable
fun MovieCategory(
    name: String,
    movies: List<Movie>,
    modifier: Modifier = Modifier,
    onNavigateToCategoryDetail: (category: String) -> Unit,
    onNavigateToMovieDetail: (id: Int) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(fontSize = 16.sp, color = Color.White, text = name, fontWeight = FontWeight.SemiBold)
            TextButton(
                onClick = { onNavigateToCategoryDetail("Popular Movies") },
                colors = ButtonDefaults.textButtonColors(

                )
            ) {
                val brush = Brush.linearGradient(listOf(
                    Cyan90,
                    Blue90
                ))

                Text(text = "See all",
                    style = TextStyle(
                        brush = brush
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(
                movies.size,
                key = { movies[it].id }
            ) {
                MovieItem(
                    movie = movies[it],
                    modifier = Modifier.width(250.dp),
                    isBackdrop = true,
                    onNavigateToMovieDetail = onNavigateToMovieDetail
                )
            }
        }
    }
}

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

        Text(fontSize = 14.sp, color = Color.White, text = movie.title)
    }
}

@Composable
fun RatingChip(
    rating: Double,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = White.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp),
            )
            .padding(4.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.pngwing),
            contentDescription = null,
            modifier = Modifier.width(36.dp),
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = BigDecimal(rating)
            .setScale(1, RoundingMode.HALF_UP)
            .toDouble()
            .toString()
        )
    }
}

//---PREVIEW
@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    MovieItem(movie = GLADIATOR_II, modifier = Modifier.width(110.dp))
}

@Preview(showBackground = true)
@Composable
fun MyHomeScreenPreview() {
    MyHomeScreen(
        onNavigateToMovieDetail = {},
        onNavigateToCategoryDetail = {}
    )
}

@Preview(showBackground = true)
@Composable
fun MovieActionPreview() {
    MovieAction(isFavorite = true)
}

@Preview()
@Composable
fun MovieInfoPreview() {
    MovieInfo(
        GLADIATOR_II.voteAverage,
        GLADIATOR_II.title
    )
}

@Preview()
@Composable
fun PopularMoviePreview() {
    PopularMovie(
        modifier = Modifier.fillMaxSize(),
        movie = GLADIATOR_II
    ) {}
}