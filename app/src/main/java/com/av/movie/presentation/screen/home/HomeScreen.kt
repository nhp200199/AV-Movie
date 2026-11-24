package com.av.movie.presentation.screen.home

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil3.compose.AsyncImage
import com.av.avmovie.R
import com.av.movie.core.utils.getFullPosterPath
import com.av.movie.dataTest.ALL_GENRES
import com.av.movie.dataTest.GLADIATOR_II
import com.av.movie.dataTest.MODEL_POPULAR_MOVIES
import com.av.movie.domain.model.Category
import com.av.movie.domain.model.Genre
import com.av.movie.domain.model.Movie
import com.av.movie.presentation.components.GenreItem
import com.av.movie.presentation.components.MovieItem
import com.av.movie.ui.theme.AVMovieTheme
import com.av.movie.ui.theme.Blue90
import com.av.movie.ui.theme.BrandGradient
import com.av.movie.ui.theme.Grey10
import com.av.movie.ui.theme.White
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private const val HOME_CAROUSEL_ENTRY_DELAY_MS = 500L
private const val CAROUSEL_AUTO_ADVANCE_DELAY_MS = 3000L
private const val CAROUSEL_PAGE_ANIMATION_MS = 650
private const val THUMBNAIL_SCROLL_ANIMATION_MS = 650
private const val THUMBNAIL_MIN_VISIBLE_RATIO = 0.7f

@Composable
fun HomeScreenVM(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToCategoryDetail: (category: Category) -> Unit,
    onNavigateToMovieDetail: (Movie) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        if (uiState is HomeUiState.Initial || uiState is HomeUiState.Error) {
            viewModel.getInitialData()
        }
    }

    LaunchedEffect(uiState) {
        Log.d("PhucNguyen", "HomeScreen: $uiState")
    }

    HomeScreen(
        uiState = uiState,
        onNavigateToCategoryDetail = onNavigateToCategoryDetail,
        onNavigateToMovieDetail = onNavigateToMovieDetail
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onNavigateToCategoryDetail: (category: Category) -> Unit,
    onNavigateToMovieDetail: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = MaterialTheme.colorScheme.background

    LazyColumn(
        contentPadding = PaddingValues(bottom = 24.dp),
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        item {
            MovieCarousel(
                modifier = Modifier.fillMaxWidth(),
                movies = (uiState as? HomeUiState.Success)?.data?.nowPlayingMovies ?: MODEL_POPULAR_MOVIES,
                onNavigateToMovieDetail = onNavigateToMovieDetail
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            MovieCategorySection(
                title = "Popular",
                category = Category.POPULAR,
                movies = (uiState as? HomeUiState.Success)?.data?.popularMovies ?: MODEL_POPULAR_MOVIES,
                onNavigateToCategoryDetail = onNavigateToCategoryDetail,
                onNavigateToMovieDetail = onNavigateToMovieDetail
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
            Genres(
                genres = (uiState as? HomeUiState.Success)?.data?.genres ?: ALL_GENRES,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
            MovieCategorySection(
                title = Category.TOP_RATED.categoryName,
                category = Category.TOP_RATED,
                movies = (uiState as? HomeUiState.Success)?.data?.topRatedMovies ?: MODEL_POPULAR_MOVIES,
                isBackdrop = true,
                onNavigateToCategoryDetail = onNavigateToCategoryDetail,
                onNavigateToMovieDetail = onNavigateToMovieDetail
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
            text = "Categories",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyHorizontalGrid(
            rows = GridCells.Fixed(3),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(225.dp)
        ) {
            items(
                count = genres.size,
                key = { genres[it].id },
                contentType = { "genre" }
            ) {
                GenreItem(
                    genre = genres[it],
                    modifier = Modifier
                        .width(150.dp)
                        .fillMaxHeight()
                )
            }
        }
    }
}

@Composable
fun MovieCarousel(
    movies: List<Movie>,
    onNavigateToMovieDetail: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    if (movies.isEmpty()) return

    val listState = rememberLazyListState()
    val isLooping = movies.size > 1
    val initialPage = remember(movies.size) {
        if (isLooping) {
            val middle = Int.MAX_VALUE / 2
            middle - (middle % movies.size)
        } else {
            0
        }
    }
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { if (isLooping) Int.MAX_VALUE else movies.size }
    )

    val pagerIsDragged by pagerState.interactionSource.collectIsDraggedAsState()
    val pageInteractionSource = remember { MutableInteractionSource() }
    val pageIsPressed by pageInteractionSource.collectIsPressedAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    var isHomeResumedReady by remember { mutableStateOf(false) }

    // Cancel active carousel work immediately when leaving RESUMED, and wait 500ms on entry/return.
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            isHomeResumedReady = false
            delay(HOME_CAROUSEL_ENTRY_DELAY_MS)
            isHomeResumedReady = true
            try {
                awaitCancellation()
            } finally {
                isHomeResumedReady = false
            }
        }
    }

    val autoAdvance = isHomeResumedReady && !pagerIsDragged && !pageIsPressed && isLooping
    var autoAdvanceResetKey by remember { mutableIntStateOf(0) }
    val pageTransitionSpec = remember {
        tween<Float>(
            durationMillis = CAROUSEL_PAGE_ANIMATION_MS,
            easing = FastOutSlowInEasing
        )
    }

    if (autoAdvance) {
        LaunchedEffect(pagerState, movies.size, autoAdvanceResetKey) {
            while (true) {
                if (pagerState.isScrollInProgress) {
                    snapshotFlow { pagerState.isScrollInProgress }.first { !it }
                }
                delay(CAROUSEL_AUTO_ADVANCE_DELAY_MS)
                pagerState.animateScrollToPage(
                    page = pagerState.currentPage + 1,
                    animationSpec = pageTransitionSpec
                )
            }
        }
    }

    if (isHomeResumedReady) {
        LaunchedEffect(pagerState, movies.size) {
            snapshotFlow { pagerState.targetPage % movies.size }.collectLatest { targetIndex ->
                ensureThumbnailVisible(listState = listState, targetIndex = targetIndex)
            }
        }
    }

    val coroutineScope = rememberCoroutineScope()
    val actualCurrentPage = pagerState.currentPage % movies.size

    Column(
        modifier = modifier
    ) {
        HorizontalPager(
            state = pagerState,
            beyondViewportPageCount = 1,
            key = { page -> "${movies[page % movies.size].id}_$page" }
        ) { page ->
            val movie = movies[page % movies.size]
            PopularMovie(
                movie = movie,
                onNavigateToMovieDetail = onNavigateToMovieDetail,
                interactionSource = pageInteractionSource
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
                val isSelected = actualCurrentPage == idx
                val thumbnailAlpha by animateFloatAsState(
                    targetValue = if (isSelected) 1f else 0.35f,
                    animationSpec = tween(durationMillis = 300),
                    label = "thumbnailAlpha"
                )
                AsyncImage(
                    model = getFullPosterPath(movies[idx].posterPath),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .width(125.dp)
                        .height(50.dp)
                        .alpha(thumbnailAlpha)
                        .clickable {
                            autoAdvanceResetKey++
                            coroutineScope.launch {
                                val targetPage = if (isLooping) {
                                    pagerState.currentPage + (idx - actualCurrentPage)
                                } else {
                                    idx
                                }
                                pagerState.animateScrollToPage(
                                    page = targetPage,
                                    animationSpec = pageTransitionSpec
                                )
                            }
                        },
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_launcher_background)
                )
            }
        }
    }
}

private suspend fun ensureThumbnailVisible(
    listState: LazyListState,
    targetIndex: Int
) {
    val layoutInfo = listState.layoutInfo
    val anchor = layoutInfo.visibleItemsInfo.firstOrNull() ?: return
    val itemInfo = layoutInfo.visibleItemsInfo.firstOrNull { it.index == targetIndex }

    val contentStart = layoutInfo.viewportStartOffset + layoutInfo.beforeContentPadding
    val contentEnd = layoutInfo.viewportEndOffset - layoutInfo.afterContentPadding
    val itemSize = itemInfo?.size ?: anchor.size
    val itemStart = itemInfo?.offset
        ?: (anchor.offset + (targetIndex - anchor.index) * (anchor.size + layoutInfo.mainAxisItemSpacing))
    val itemEnd = itemStart + itemSize

    val visibleWidth = (minOf(itemEnd, contentEnd) - maxOf(itemStart, contentStart)).coerceAtLeast(0)
    val visibleRatio = if (itemSize > 0) visibleWidth.toFloat() / itemSize else 0f

    if (visibleRatio >= THUMBNAIL_MIN_VISIBLE_RATIO) return

    val scrollDelta = when {
        itemEnd > contentEnd -> (itemEnd - contentEnd).toFloat()
        itemStart < contentStart -> (itemStart - contentStart).toFloat()
        else -> 0f
    }
    if (scrollDelta != 0f) {
        listState.animateScrollBy(
            value = scrollDelta,
            animationSpec = tween(
                durationMillis = THUMBNAIL_SCROLL_ANIMATION_MS,
                easing = FastOutSlowInEasing
            )
        )
    }
}

@Composable
fun PopularMovie(
    movie: Movie,
    onNavigateToMovieDetail: (Movie) -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onMyListClick: (Movie) -> Unit = {}
) {
    var isInMyList by remember(movie.id, movie.isFavorite) { mutableStateOf(movie.isFavorite) }
    val isDarkTheme = isSystemInDarkTheme()
    val bottomScrimColor = if (isDarkTheme) {
        MaterialTheme.colorScheme.background
    } else {
        Color.Black.copy(alpha = 0.85f)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(430.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current
            ) { onNavigateToMovieDetail(movie) }
    ) {
        AsyncImage(
            model = getFullPosterPath(movie.posterPath),
            contentDescription = movie.title,
            alignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    val topGradient = Brush.verticalGradient(
                        colors = listOf(
                            Grey10.copy(alpha = 0.45f),
                            Color.Transparent
                        ),
                        startY = 0f,
                        endY = size.height * 0.22f
                    )
                    val bottomGradient = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            bottomScrimColor.copy(alpha = 0.75f),
                            bottomScrimColor
                        ),
                        startY = size.height * 0.35f,
                        endY = size.height
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(topGradient)
                        drawRect(bottomGradient)
                    }
                },
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.ic_launcher_background),
            placeholder = painterResource(id = R.drawable.ic_launcher_background)
        )

        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Text(
                text = movie.title,
                color = White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))

            HomeHeroActions(
                isFavorite = isInMyList,
                onPlayClick = { onNavigateToMovieDetail(movie) },
                onMyListClick = {
                    isInMyList = !isInMyList
                    onMyListClick(movie)
                }
            )
        }
    }
}

@Composable
private fun HomeHeroActions(
    isFavorite: Boolean,
    onPlayClick: () -> Unit,
    onMyListClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(40.dp)
                .clip(CircleShape)
                .background(brush = BrandGradient)
                .clickable(onClick = onPlayClick)
                .padding(horizontal = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .background(color = White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = null,
                    tint = Blue90,
                    modifier = Modifier.size(13.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Play",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = White
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(40.dp)
                .clip(CircleShape)
                .background(color = Color.Black.copy(alpha = 0.25f))
                .border(width = 1.5.dp, color = White, shape = CircleShape)
                .clickable(onClick = onMyListClick)
                .padding(horizontal = 20.dp)
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Check else Icons.Filled.Add,
                contentDescription = if (isFavorite) "Added to My List" else "Add to My List",
                tint = White,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "My List",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = White
            )
        }
    }
}

@Composable
fun MovieCategorySection(
    title: String,
    category: Category,
    movies: List<Movie>,
    onNavigateToCategoryDetail: (category: Category) -> Unit,
    onNavigateToMovieDetail: (Movie) -> Unit,
    modifier: Modifier = Modifier,
    isBackdrop: Boolean = false,
    cardWidth: Dp = if (isBackdrop) 250.dp else 148.dp
) {
    val contentColor = MaterialTheme.colorScheme.onBackground

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = contentColor
            )

            Text(
                text = "See all",
                style = TextStyle(
                    brush = BrandGradient,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onNavigateToCategoryDetail(category) }
                    .padding(horizontal = 4.dp, vertical = 6.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                count = movies.size,
                key = { movies[it].id }
            ) { index ->
                MovieItem(
                    movie = movies[index],
                    isBackdrop = isBackdrop,
                    titleColor = contentColor,
                    modifier = Modifier.width(cardWidth),
                    onNavigateToMovieDetail = onNavigateToMovieDetail
                )
            }
        }
    }
}

//---PREVIEW
@Preview(showBackground = true)
@Composable
fun HomeScreenLightPreview() {
    AVMovieTheme(darkTheme = false) {
        HomeScreen(
            uiState = HomeUiState.Success(HomeData(nowPlayingMovies = MODEL_POPULAR_MOVIES)),
            onNavigateToMovieDetail = {},
            onNavigateToCategoryDetail = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenDarkPreview() {
    AVMovieTheme(darkTheme = true) {
        HomeScreen(
            uiState = HomeUiState.Success(HomeData(nowPlayingMovies = MODEL_POPULAR_MOVIES)),
            onNavigateToMovieDetail = {},
            onNavigateToCategoryDetail = {}
        )
    }
}

@Preview
@Composable
fun PopularMoviePreview() {
    AVMovieTheme(darkTheme = false) {
        PopularMovie(
            modifier = Modifier.fillMaxWidth(),
            movie = GLADIATOR_II,
            onNavigateToMovieDetail = {}
        )
    }
}