package com.av.movie.presentation.screen.movieDetail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material3.Divider
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.av.avmovie.R
import com.av.movie.data.model.CastDTO
import com.av.movie.dataTest.GLADIATOR_II
import com.av.movie.dataTest.formatDate
import com.av.movie.dataTest.formatTime
import com.av.movie.dataTest.getFullBackdropPath
import com.av.movie.dataTest.getFullPosterPath
import com.av.movie.data.model.Movie
import com.av.movie.data.model.MovieDetail
import com.av.movie.data.model.VideoDTO
import com.av.movie.dataTest.MODEL_CASTS
import com.av.movie.dataTest.MODEL_GLADIATOR_VIDEO
import com.av.movie.dataTest.MODEL_MOVIE_DETAIL
import com.av.movie.dataTest.MODEL_POPULAR_MOVIES
import com.av.movie.dataTest.getCastProfilePath
import com.av.movie.domain.usecase.FullDetailMovie
import com.av.movie.presentation.screen.home.MovieAction
import com.av.movie.presentation.screen.home.MovieInfo
import com.av.movie.presentation.screen.home.MovieItem
import com.av.movie.presentation.screen.home.RatingChip
import com.av.movie.ui.theme.Blue90
import com.av.movie.ui.theme.Cyan90
import com.av.movie.ui.theme.Grey10
import com.av.movie.ui.theme.LightGrey10
import com.av.movie.ui.theme.LightGrey30
import com.av.movie.ui.theme.LightGrey50
import kotlinx.coroutines.launch

enum class OtherInformationScreen(
    val title: String
) {
    VIDEOS("Videos"),
    MORE_LIKE_THIS("More like this"),
    ABOUT("About")
}

@Composable
fun MovieDetailScreenVM(
    movieId: Int,
    viewModel: MovieDetailViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.loadMovieDetail(movieId)
    }

    val uiState by viewModel.detailMovieState.collectAsStateWithLifecycle()

    Log.d("MovieDetail", "uiState = $uiState")

    MovieDetailScreen(uiState)
}

@Composable
fun MovieDetailScreen(
    uiState: MovieDetailUiState
) {
    if (uiState is MovieDetailUiState.Success) {
        val data = uiState.data
        val movie = data.movieDetail
        val recommendations = data.recommendations
        val videos = data.videos.results
        val casts = data.casts.cast

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Grey10),
        ) {
            Headline(
                movie = movie,
                modifier = Modifier.fillMaxWidth()
                    .height(300.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Overview(
                movie = movie
            )

            Spacer(modifier = Modifier.height(32.dp))

            OtherInformation(
                movie = movie,
                recommendations = recommendations,
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth(),
                videos = videos,
                casts = casts
            )
        }
    }
}

@Composable
fun OtherInformation(
    casts: List<CastDTO>,
    videos: List<VideoDTO>,
    movie: MovieDetail,
    recommendations: List<Movie>,
    modifier: Modifier = Modifier,
    pages: Array<OtherInformationScreen> = OtherInformationScreen.entries.toTypedArray()
) {
    val pagerState = rememberPagerState(
        pageCount = { pages.size },
        initialPage = 0
    )

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
    ) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Grey10,
            divider = {
                Divider(color = LightGrey50)
            },
            indicator = { tabPositions ->
                if (pagerState.currentPage < tabPositions.size) {
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = Blue90
                    )
                }
            }
        ) {
            pages.forEachIndexed { idx, otherInformationScreen ->
                val tabSelected = pagerState.currentPage == idx

                Tab(
                    selected = tabSelected,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(idx)
                        }
                    },
                ) {
                    val brush = Brush.horizontalGradient(
                        colors = listOf(
                            Blue90,
                            Cyan90
                        )
                    )
                    Text(
                        text = otherInformationScreen.title,
                        modifier = Modifier.padding(vertical = 16.dp),
                        style = TextStyle(
                            brush = if (tabSelected) brush else null
                        ),
                        color = LightGrey50
                    )
                }
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp)
        ) {
            when(pages[it]) {
                OtherInformationScreen.VIDEOS -> VideoSection(videos)
                OtherInformationScreen.MORE_LIKE_THIS -> MoreLikeThisSection(recommendations)
                OtherInformationScreen.ABOUT -> AboutSection(movie = movie, casts = casts)
            }
        }
    }
}

@Composable
fun GenreChip(
    name: String,
    modifier: Modifier = Modifier
) {
    val brush = Brush.horizontalGradient(
        colors = listOf(
            Blue90,
            Cyan90
        )
    )

    Text(
        text = name,
        modifier = modifier
            .border(
                width = 1.dp,
                brush = brush,
                shape = RoundedCornerShape(50)
            )
            .clip(RoundedCornerShape(50))
            .background(color = LightGrey30)
            .padding(PaddingValues(horizontal = 8.dp, vertical = 4.dp)),
        style = TextStyle(
            brush = brush
        )
    )
}

@Composable
fun VideoSection(
    data: List<VideoDTO>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(
            start = 8.dp,
            end = 8.dp,
            bottom = 16.dp
        )
    ) {
        items(
            count =  data.size,
            key = { data[it].id }
        ) {
            TrailerItem(
                data = data[it],
                backdrop = GLADIATOR_II.backdropPath
            )
        }
    }
}

@Composable
fun MoreLikeThisSection(
    recommendations: List<Movie>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 8.dp,
            end = 8.dp,
            bottom = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {

        items(recommendations.size) {idx ->
            MovieItem(
                movie = recommendations[idx]
            )
        }
    }
}

@Composable
fun AboutSection(
    casts: List<CastDTO>,
    movie: MovieDetail,
    modifier: Modifier = Modifier
) {
    val aboutData = listOf(
        Pair<String, String>("Country", movie.originalLanguage),
        Pair<String, String>("Year", formatDate("yyyy", movie.releaseDate, originPattern = "yyyy-MM-dd")),
        Pair<String, String>("Duration", formatTime(movie.duration)),
        Pair<String, String>("Language", movie.originalLanguage)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 8.dp,
            end = 8.dp,
            bottom = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            count = aboutData.size,
            key = { aboutData[it].first }
        ) {
            AboutItem(
                title = aboutData[it].first,
                description = aboutData[it].second
            )
        }

        item(
            span = { GridItemSpan(2) },
            content = {
                Text("Casts", fontSize = 14.sp, color = Color.White)
            }
        )

        item(
            span = { GridItemSpan(2) },
            content = {
                Casts(casts)
            }
        )
    }
}

@Composable
fun Casts(
    casts: List<CastDTO>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            count = casts.size,
            key = { casts[it].id },
            itemContent = { CastItem(cast = casts[it], modifier = Modifier.width(138.dp)) }
        )
    }
}

@Composable
fun CastItem(
    cast: CastDTO,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
//            .clickable { onNavigateToMovieDetail(movie.id) },
    ) {
        AsyncImage(
            model = getCastProfilePath(cast.profilePath),
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            error = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = cast.name,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(fontSize = 14.sp, color = Color.White, text = cast.name)

        Spacer(modifier = Modifier.height(4.dp))

        Text(fontSize = 14.sp, color = LightGrey10, text = cast.character)
    }
}

@Composable
fun AboutItem(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = TextStyle(
                color = Color.White,
                fontSize = 14.sp
            )
        )
        Text(
            text = description,
            style = TextStyle(
                color = LightGrey10,
                fontSize = 14.sp
            )
        )
    }

}

@Composable
fun Overview(
    movie: MovieDetail,
    modifier: Modifier = Modifier
) {
    val lengthThreshold = 200
    val shouldShowMore = movie.overview.length > lengthThreshold

    var showFullText by remember { mutableStateOf(false) }

    val description = if (showFullText) {
        movie.overview
    } else {
        movie.overview.take(lengthThreshold)
    }

    val action = if (showFullText) " Show less"
        else " Show more"

    Column(
        modifier = modifier
            .padding(horizontal = 8.dp),
    ) {
        Text(
            buildAnnotatedString {
                append(description)
                if (shouldShowMore) withStyle(style = SpanStyle(color = Blue90)) {
                    pushLink(LinkAnnotation.Clickable(action) {
                        showFullText = !showFullText
                    })
                    append(action)
                }
            },
            style = TextStyle(
                color = LightGrey10,
                fontSize = 14.sp
            )
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Headline(
    movie: MovieDetail,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
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

            FlowRow(
                overflow = FlowRowOverflow.Clip,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                movie.genres.forEach {
                    GenreChip(name = it.name)
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Spacer(Modifier.height(16.dp))
            MovieAction(movie.isFavorite)
        }
    }
}

@Composable
fun TrailerItem(
    data: VideoDTO,
    backdrop: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = getFullBackdropPath(backdrop),
            contentDescription = data.name,
            error = painterResource(id = R.drawable.ic_launcher_background),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(120.dp)
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
                .fillMaxHeight()
        ) {
            Text(
                text = data.name,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 14.sp
                )
            )
            Text(
                text = formatDate("dd-MM-yyyy", data.publishedAt, originPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
                style = TextStyle(
                    color = LightGrey10,
                    fontSize = 14.sp
                )
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF090E17
)
@Composable
fun  MovieDetailScreenPreview() {
    MovieDetailScreen(
        uiState = MovieDetailUiState.Success(
            data = FullDetailMovie(
                movieDetail = MODEL_MOVIE_DETAIL,
                casts = MODEL_CASTS,
                videos = MODEL_GLADIATOR_VIDEO,
                recommendations = MODEL_POPULAR_MOVIES
            )
        )
    )
}