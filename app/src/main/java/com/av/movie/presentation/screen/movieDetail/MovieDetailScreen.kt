package com.av.movie.presentation.screen.movieDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.av.avmovie.R
import com.av.movie.core.utils.formatDate
import com.av.movie.core.utils.formatTime
import com.av.movie.core.utils.getCastProfilePath
import com.av.movie.core.utils.getFullBackdropPath
import com.av.movie.core.utils.getFullPosterPath
import com.av.movie.dataTest.MODEL_CASTS
import com.av.movie.dataTest.MODEL_GLADIATOR_VIDEO
import com.av.movie.dataTest.MODEL_MOVIE_DETAIL
import com.av.movie.dataTest.MODEL_POPULAR_MOVIES
import com.av.movie.domain.model.Cast
import com.av.movie.domain.model.Movie
import com.av.movie.domain.model.MovieDetail
import com.av.movie.domain.model.Video
import com.av.movie.domain.usecase.FullDetailMovie
import com.av.movie.presentation.components.MovieAction
import com.av.movie.presentation.components.MovieInfo
import com.av.movie.presentation.components.MovieItem
import com.av.movie.ui.theme.AVMovieTheme
import com.av.movie.ui.theme.BrandGradient
import com.av.movie.ui.theme.BrandLightTint
import com.av.movie.ui.theme.BrandPrimary
import com.av.movie.ui.theme.Grey10
import com.av.movie.ui.theme.LightGrey30
import com.av.movie.ui.theme.White
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

enum class OtherInformationScreen(
    val title: String
) {
    TRAILERS("Trailers"),
    MORE_LIKE_THIS("More like this"),
    ABOUT("About")
}

@Composable
fun MovieDetailScreenVM(
    movie: Movie,
    onBackClick: () -> Unit = {},
    onNavigateToMovieDetail: (Movie) -> Unit = {},
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(movie.id) {
        viewModel.loadMovieDetail(movie)
    }

    val uiState by viewModel.detailMovieState.collectAsStateWithLifecycle()
    val systemUiController = rememberSystemUiController()
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = MaterialTheme.colorScheme.background
    var isToolbarCollapsed by remember { mutableStateOf(false) }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = if (isToolbarCollapsed) !isDarkTheme else false
        )
        systemUiController.setNavigationBarColor(
            color = backgroundColor,
            darkIcons = !isDarkTheme
        )
    }

    MovieDetailScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onSaveClick = viewModel::toggleSave,
        onRetry = viewModel::retry,
        onNavigateToMovieDetail = onNavigateToMovieDetail,
        onToolbarCollapsedChanged = { isToolbarCollapsed = it }
    )
}

@Composable
fun MovieDetailScreen(
    uiState: MovieDetailUiState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onPlayClick: () -> Unit = {},
    onSaveClick: () -> Unit = {},
    onRetry: () -> Unit = {},
    onNavigateToMovieDetail: (Movie) -> Unit = {},
    onToolbarCollapsedChanged: (Boolean) -> Unit = {}
) {
    val detail = uiState.data
    val backgroundColor = MaterialTheme.colorScheme.background

    if (detail == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            when (uiState) {
                is MovieDetailUiState.Error -> {
                    DetailErrorSection(
                        onRetry = onRetry,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    CircularProgressIndicator(
                        color = BrandPrimary,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            MovieDetailPinnedTopBar(
                title = "",
                collapseFraction = 1f,
                onBackClick = onBackClick,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
        return
    }

    val movie = detail.movieDetail
    val density = LocalDensity.current
    val initialToolbarHeightPx = with(density) {
        WindowInsets.statusBars.getTop(this) + 56.dp.roundToPx()
    }
    var collapsedToolbarHeightPx by remember { mutableIntStateOf(initialToolbarHeightPx) }
    var fullHeaderHeightPx by remember { mutableIntStateOf(0) }
    var headerOffsetPx by remember { mutableFloatStateOf(0f) }

    val collapseFraction by remember {
        derivedStateOf {
            val maxCollapse = (fullHeaderHeightPx - collapsedToolbarHeightPx).coerceAtLeast(1)
            val clampedOffset = headerOffsetPx.coerceIn(-maxCollapse.toFloat(), 0f)
            (-clampedOffset / maxCollapse.toFloat()).coerceIn(0f, 1f)
        }
    }

    val isToolbarCollapsed by remember {
        derivedStateOf { collapseFraction >= TOOLBAR_COLLAPSE_THRESHOLD }
    }
    LaunchedEffect(isToolbarCollapsed) {
        onToolbarCollapsedChanged(isToolbarCollapsed)
    }

    fun applyHeaderScrollDelta(delta: Float): Float {
        val maxCollapse = (fullHeaderHeightPx - collapsedToolbarHeightPx).coerceAtLeast(0)
        val oldOffset = headerOffsetPx
        val newOffset = (oldOffset + delta).coerceIn(-maxCollapse.toFloat(), 0f)
        headerOffsetPx = newOffset
        return newOffset - oldOffset
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                return if (delta < 0f) Offset(0f, applyHeaderScrollDelta(delta)) else Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                val delta = available.y
                return if (delta > 0f) Offset(0f, applyHeaderScrollDelta(delta)) else Offset.Zero
            }
        }
    }

    val headerScrollableState = rememberScrollableState { delta ->
        applyHeaderScrollDelta(delta)
    }

    val pages = remember { OtherInformationScreen.entries.toTypedArray() }
    val pagerState = rememberPagerState(
        pageCount = { pages.size },
        initialPage = 0
    )
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .nestedScroll(nestedScrollConnection)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Collapsing header: Hero + Title/Rating + Play/Save + Genres + Overview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clipToBounds()
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(
                            constraints.copy(minHeight = 0, maxHeight = Constraints.Infinity)
                        )
                        if (fullHeaderHeightPx != placeable.height) {
                            fullHeaderHeightPx = placeable.height
                        }
                        val maxCollapse = (placeable.height - collapsedToolbarHeightPx).coerceAtLeast(0)
                        val clampedOffset = headerOffsetPx.coerceIn(-maxCollapse.toFloat(), 0f).roundToInt()
                        val minVisibleHeight = collapsedToolbarHeightPx.coerceAtMost(placeable.height)
                        val visibleHeight = (placeable.height + clampedOffset)
                            .coerceIn(minVisibleHeight, placeable.height)
                        layout(placeable.width, visibleHeight) {
                            placeable.placeRelative(0, clampedOffset)
                        }
                    }
                    .scrollable(
                        orientation = Orientation.Vertical,
                        state = headerScrollableState
                    )
            ) {
                MovieDetailHeaderContent(
                    movie = movie,
                    onPlayClick = onPlayClick,
                    onSaveClick = onSaveClick
                )
            }

            // Docked 3-Tab bar below the collapsing header / pinned toolbar
            MovieDetailTabBar(
                pages = pages,
                selectedTabIndex = pagerState.currentPage,
                onTabSelected = { index ->
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                modifier = Modifier.scrollable(
                    orientation = Orientation.Vertical,
                    state = headerScrollableState
                )
            )

            // Tab content fills all remaining height down to bottom of screen
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { pageIndex ->
                when (uiState) {
                    is MovieDetailUiState.Loading -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator(color = BrandPrimary)
                        }
                    }

                    is MovieDetailUiState.Error -> {
                        DetailErrorSection(
                            onRetry = onRetry,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    is MovieDetailUiState.Success,
                    MovieDetailUiState.Initial -> {
                        when (pages[pageIndex]) {
                            OtherInformationScreen.TRAILERS -> VideoSection(
                                data = detail.videos,
                                movieBackdrop = movie.backdropPath.ifBlank { movie.posterPath },
                                modifier = Modifier.fillMaxSize()
                            )

                            OtherInformationScreen.MORE_LIKE_THIS -> MoreLikeThisSection(
                                recommendations = detail.recommendations,
                                onNavigateToMovieDetail = onNavigateToMovieDetail,
                                modifier = Modifier.fillMaxSize()
                            )

                            OtherInformationScreen.ABOUT -> AboutSection(
                                movie = movie,
                                casts = detail.casts,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }

        // Pinned Top Toolbar Overlay (Back button sits higher; Title & bg fade in as header collapses)
        MovieDetailPinnedTopBar(
            title = movie.title,
            collapseFraction = collapseFraction,
            onBackClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .onSizeChanged { collapsedToolbarHeightPx = it.height }
        )
    }
}

@Composable
private fun MovieDetailPinnedTopBar(
    title: String,
    collapseFraction: Float,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val toolbarAlpha = ((collapseFraction - TOOLBAR_COLLAPSE_THRESHOLD) / (1f - TOOLBAR_COLLAPSE_THRESHOLD))
        .coerceIn(0f, 1f)
    val backgroundColor = MaterialTheme.colorScheme.background
    val iconTint = lerp(White, MaterialTheme.colorScheme.onBackground, toolbarAlpha)
    val backButtonScrimAlpha = 0.35f * (1f - toolbarAlpha)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor.copy(alpha = toolbarAlpha))
            .statusBarsPadding()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 8.dp)
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = backButtonScrimAlpha))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = iconTint
                )
            }

            if (title.isNotEmpty()) {
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .alpha(toolbarAlpha)
                )
            }
        }

        if (toolbarAlpha > 0.05f) {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f * toolbarAlpha)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MovieDetailHeaderContent(
    movie: MovieDetail,
    onPlayClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = MaterialTheme.colorScheme.background

    Column(modifier = modifier.fillMaxWidth()) {
        // Hero image bleeding under status bar (no streaming icon)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp)
        ) {
            AsyncImage(
                model = getFullPosterPath(movie.posterPath.ifBlank { movie.backdropPath }),
                contentDescription = movie.title,
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_launcher_background),
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithCache {
                        val topScrim = Brush.verticalGradient(
                            colors = listOf(
                                Grey10.copy(alpha = 0.55f),
                                Color.Transparent
                            ),
                            startY = 0f,
                            endY = size.height * 0.28f
                        )
                        val bottomScrim = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                backgroundColor.copy(alpha = 0.65f),
                                backgroundColor
                            ),
                            startY = size.height * 0.58f,
                            endY = size.height
                        )
                        onDrawWithContent {
                            drawContent()
                            drawRect(topScrim)
                            drawRect(bottomScrim)
                        }
                    }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            MovieInfo(
                avgRating = movie.voteAverage,
                title = movie.title,
                releaseDate = movie.releaseDate
            )

            Spacer(modifier = Modifier.height(16.dp))

            MovieAction(
                isSaved = movie.isFavorite,
                onPlayClick = onPlayClick,
                onSaveClick = onSaveClick
            )

            if (movie.genres.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                FlowRow(
                    overflow = FlowRowOverflow.Clip,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    movie.genres.forEach { genre ->
                        GenreChip(name = genre.name)
                    }
                }
            }

            if (movie.overview.isNotBlank()) {
                Spacer(modifier = Modifier.height(14.dp))
                Overview(movie = movie)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun MovieDetailTabBar(
    pages: Array<OtherInformationScreen>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier,
        divider = {
            HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        },
        indicator = { tabPositions ->
            if (selectedTabIndex < tabPositions.size) {
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .height(3.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp))
                        .background(BrandGradient)
                )
            }
        }
    ) {
        pages.forEachIndexed { idx, tab ->
            val tabSelected = selectedTabIndex == idx
            Tab(
                selected = tabSelected,
                onClick = { onTabSelected(idx) }
            ) {
                Text(
                    text = tab.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 14.dp),
                    style = TextStyle(
                        brush = if (tabSelected) BrandGradient else null,
                        fontSize = 14.sp,
                        fontWeight = if (tabSelected) FontWeight.SemiBold else FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f)
                )
            }
        }
    }
}

@Composable
fun GenreChip(
    name: String,
    modifier: Modifier = Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()
    val chipBackground = if (isDarkTheme) LightGrey30 else BrandLightTint

    Text(
        text = name,
        modifier = modifier
            .border(
                width = 1.dp,
                brush = BrandGradient,
                shape = RoundedCornerShape(50)
            )
            .clip(RoundedCornerShape(50))
            .background(color = chipBackground)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        style = TextStyle(
            brush = BrandGradient,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    )
}

@Composable
fun VideoSection(
    data: List<Video>,
    movieBackdrop: String,
    modifier: Modifier = Modifier
) {
    val navBarBottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    if (data.isEmpty()) {
        EmptyTabMessage(text = "No trailers available", modifier = modifier)
        return
    }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 24.dp + navBarBottom
        )
    ) {
        items(
            count = data.size,
            key = { data[it].id }
        ) { idx ->
            TrailerItem(
                data = data[idx],
                backdrop = movieBackdrop
            )
        }
    }
}

@Composable
fun MoreLikeThisSection(
    recommendations: List<Movie>,
    onNavigateToMovieDetail: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    val navBarBottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    if (recommendations.isEmpty()) {
        EmptyTabMessage(text = "No similar movies found", modifier = modifier)
        return
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 24.dp + navBarBottom
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(
            count = recommendations.size,
            key = { recommendations[it].id }
        ) { idx ->
            MovieItem(
                movie = recommendations[idx],
                titleColor = MaterialTheme.colorScheme.onBackground,
                onNavigateToMovieDetail = onNavigateToMovieDetail
            )
        }
    }
}

@Composable
fun AboutSection(
    casts: List<Cast>,
    movie: MovieDetail,
    modifier: Modifier = Modifier
) {
    val navBarBottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val countryDisplay = movie.country.ifBlank { movie.originalLanguage.uppercase() }
    val yearDisplay = formatDate("yyyy", movie.releaseDate).ifBlank { "--" }
    val durationDisplay = if (movie.duration > 0) formatTime(movie.duration) else "--"
    val languageDisplay = movie.originalLanguage.uppercase().ifBlank { "--" }

    val aboutData = listOf(
        Pair("Country", countryDisplay),
        Pair("Year", yearDisplay),
        Pair("Duration", durationDisplay),
        Pair("Language", languageDisplay)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 24.dp + navBarBottom
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
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

        if (casts.isNotEmpty()) {
            item(
                span = { GridItemSpan(2) },
                content = {
                    Text(
                        text = "Casts",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 6.dp)
                    )
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
}

@Composable
fun Casts(
    casts: List<Cast>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            count = casts.size,
            key = { "${casts[it].id}_${casts[it].creditId}" },
            itemContent = { CastItem(cast = casts[it], modifier = Modifier.width(120.dp)) }
        )
    }
}

@Composable
fun CastItem(
    cast: Cast,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        AsyncImage(
            model = getCastProfilePath(cast.profilePath),
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            error = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = cast.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(138f / 175f)
                .clip(RoundedCornerShape(10.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = cast.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = cast.character,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
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
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun Overview(
    movie: MovieDetail,
    modifier: Modifier = Modifier
) {
    val lengthThreshold = 160
    val shouldShowMore = movie.overview.length > lengthThreshold

    var showFullText by remember { mutableStateOf(false) }

    val description = if (showFullText || !shouldShowMore) {
        movie.overview
    } else {
        "${movie.overview.take(lengthThreshold).trimEnd()}..."
    }

    val action = if (showFullText) " Show less" else " View More"

    Column(
        modifier = modifier
    ) {
        Text(
            text = buildAnnotatedString {
                append(description)
                if (shouldShowMore) {
                    withStyle(
                        style = SpanStyle(
                            color = BrandPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        pushLink(LinkAnnotation.Clickable(action) {
                            showFullText = !showFullText
                        })
                        append(action)
                        pop()
                    }
                }
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}

@Composable
fun TrailerItem(
    data: Video,
    backdrop: String,
    modifier: Modifier = Modifier
) {
    val createdTime = formatTrailerPublishedAt(data.publishedAt)

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(148.dp)
                .aspectRatio(16f / 10f)
                .clip(RoundedCornerShape(12.dp))
        ) {
            AsyncImage(
                model = getFullBackdropPath(backdrop),
                contentDescription = data.name,
                placeholder = painterResource(id = R.drawable.ic_launcher_background),
                error = painterResource(id = R.drawable.ic_launcher_background),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(White.copy(alpha = 0.9f))
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Play trailer",
                    tint = Grey10,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(start = 14.dp)
                .weight(1f)
        ) {
            Text(
                text = data.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            if (createdTime.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = createdTime,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun EmptyTabMessage(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.padding(24.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun DetailErrorSection(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(24.dp)
    ) {
        Text(
            text = "Could not load movie details",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(CircleShape)
                .background(BrandGradient)
                .clickable(onClick = onRetry)
                .padding(horizontal = 24.dp, vertical = 10.dp)
        ) {
            Text(
                text = "Retry",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )
        }
    }
}

private const val TOOLBAR_COLLAPSE_THRESHOLD = 0.72f

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenDarkPreview() {
    AVMovieTheme(darkTheme = true) {
        MovieDetailScreen(
            uiState = MovieDetailUiState.Success(
                successData = FullDetailMovie(
                    movieDetail = MODEL_MOVIE_DETAIL.copy(country = "United States"),
                    casts = MODEL_CASTS,
                    videos = MODEL_GLADIATOR_VIDEO,
                    recommendations = MODEL_POPULAR_MOVIES
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenLightPreview() {
    AVMovieTheme(darkTheme = false) {
        MovieDetailScreen(
            uiState = MovieDetailUiState.Success(
                successData = FullDetailMovie(
                    movieDetail = MODEL_MOVIE_DETAIL.copy(country = "United States"),
                    casts = MODEL_CASTS,
                    videos = MODEL_GLADIATOR_VIDEO,
                    recommendations = MODEL_POPULAR_MOVIES
                )
            )
        )
    }
}
