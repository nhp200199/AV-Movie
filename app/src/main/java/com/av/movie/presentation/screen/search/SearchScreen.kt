package com.av.movie.presentation.screen.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.av.avmovie.R
import com.av.movie.dataTest.MODEL_POPULAR_MOVIES
import com.av.movie.domain.model.Movie
import com.av.movie.domain.model.SearchMovieFilter
import com.av.movie.domain.model.SearchSortOption
import com.av.movie.presentation.components.MovieItem
import com.av.movie.ui.theme.AVMovieTheme
import com.av.movie.ui.theme.BrandGradient
import com.av.movie.ui.theme.BrandPrimary
import com.av.movie.ui.theme.White
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SearchScreenVM(
    onNavigateBack: () -> Unit,
    onNavigateToMovieDetail: (Int) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = MaterialTheme.colorScheme.background
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = backgroundColor,
            darkIcons = !isDarkTheme
        )
        systemUiController.setNavigationBarColor(
            color = backgroundColor,
            darkIcons = !isDarkTheme
        )
    }

    SearchScreen(
        query = query,
        uiState = uiState,
        onQueryChange = viewModel::onQueryChange,
        onSearchSubmit = viewModel::onSearchSubmit,
        onClearQuery = viewModel::onClearQuery,
        onRecentSearchClick = viewModel::onRecentSearchClick,
        onRemoveRecentSearch = viewModel::onRemoveRecentSearch,
        onApplyFilter = viewModel::onApplyFilter,
        onRemoveFilterChip = viewModel::onRemoveFilterChip,
        onRetry = viewModel::onRetry,
        onBackClick = onNavigateBack,
        onMovieClick = { movieId ->
            viewModel.onMovieClick()
            onNavigateToMovieDetail(movieId)
        }
    )
}

@Composable
fun SearchScreen(
    query: String,
    uiState: SearchUiState,
    onQueryChange: (String) -> Unit,
    onSearchSubmit: () -> Unit,
    onClearQuery: () -> Unit,
    onRecentSearchClick: (String) -> Unit,
    onRemoveRecentSearch: (String) -> Unit,
    onApplyFilter: (SearchMovieFilter) -> Unit,
    onRemoveFilterChip: (ActiveFilterChip) -> Unit,
    onRetry: () -> Unit,
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    autoFocus: Boolean = true
) {
    var showFilterSheet by remember { mutableStateOf(false) }
    var hasRequestedInitialFocus by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    if (autoFocus) {
        LaunchedEffect(Unit) {
            if (!hasRequestedInitialFocus && query.isEmpty()) {
                focusRequester.requestFocus()
            }
            hasRequestedInitialFocus = true
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        SearchHeader(
            query = query,
            showFilterIcon = uiState.showFilterIcon,
            focusRequester = focusRequester,
            onBackClick = onBackClick,
            onQueryChange = onQueryChange,
            onSearchSubmit = {
                focusManager.clearFocus()
                keyboardController?.hide()
                onSearchSubmit()
            },
            onClearQuery = onClearQuery,
            onFilterClick = {
                focusManager.clearFocus()
                keyboardController?.hide()
                showFilterSheet = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 16.dp, top = 12.dp, bottom = 8.dp)
        )

        if (query.isBlank()) {
            if (uiState.recentSearches.isNotEmpty()) {
                RecentSearchesSection(
                    recentSearches = uiState.recentSearches,
                    onItemClick = { keyword ->
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        onRecentSearchClick(keyword)
                    },
                    onRemoveClick = onRemoveRecentSearch,
                    modifier = Modifier.fillMaxSize()
                )
            }
        } else {
            if (uiState.activeFilterChips.isNotEmpty()) {
                ActiveFilterChipsRow(
                    chips = uiState.activeFilterChips,
                    onRemoveChip = onRemoveFilterChip,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )
            }

            SearchResultBody(
                resultState = uiState.resultState,
                filterRevision = uiState.filterRevision,
                onMovieClick = onMovieClick,
                onRetry = onRetry,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }

    if (showFilterSheet) {
        SearchFilterBottomSheet(
            initialFilter = uiState.activeFilter,
            availableFilters = uiState.availableFilters,
            onDismiss = { showFilterSheet = false },
            onApply = onApplyFilter
        )
    }
}

@Composable
private fun SearchHeader(
    query: String,
    showFilterIcon: Boolean,
    focusRequester: FocusRequester,
    onBackClick: () -> Unit,
    onQueryChange: (String) -> Unit,
    onSearchSubmit: () -> Unit,
    onClearQuery: () -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }
    val searchFieldShape = RoundedCornerShape(14.dp)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .clip(searchFieldShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .then(
                    if (isFocused) {
                        Modifier.border(width = 1.5.dp, color = BrandPrimary, shape = searchFieldShape)
                    } else {
                        Modifier
                    }
                )
                .padding(horizontal = 14.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = if (isFocused || query.isNotEmpty()) {
                    BrandPrimary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                },
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.weight(1f)
            ) {
                if (query.isEmpty()) {
                    Text(
                        text = "Search movies...",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.65f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    singleLine = true,
                    cursorBrush = SolidColor(BrandPrimary),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { onSearchSubmit() }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged { isFocused = it.isFocused }
                )
            }

            if (query.isNotEmpty()) {
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear query",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(onClick = onClearQuery)
                )
            }
        }

        AnimatedVisibility(
            visible = showFilterIcon,
            enter = slideInHorizontally(
                animationSpec = tween(durationMillis = FILTER_ICON_ANIM_MS),
                initialOffsetX = { fullWidth -> fullWidth }
            ) + expandHorizontally(
                animationSpec = tween(durationMillis = FILTER_ICON_ANIM_MS),
                expandFrom = Alignment.End
            ) + fadeIn(animationSpec = tween(durationMillis = FILTER_ICON_ANIM_MS)),
            exit = slideOutHorizontally(
                animationSpec = tween(durationMillis = FILTER_ICON_ANIM_MS),
                targetOffsetX = { fullWidth -> fullWidth }
            ) + shrinkHorizontally(
                animationSpec = tween(durationMillis = FILTER_ICON_ANIM_MS),
                shrinkTowards = Alignment.End
            ) + fadeOut(animationSpec = tween(durationMillis = FILTER_ICON_ANIM_MS))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(BrandPrimary.copy(alpha = 0.14f))
                        .clickable(onClick = onFilterClick)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = "Sort and filter",
                        tint = BrandPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun RecentSearchesSection(
    recentSearches: List<String>,
    onItemClick: (String) -> Unit,
    onRemoveClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        item(key = "recent_header") {
            Text(
                text = "Recent Searchs",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        items(
            items = recentSearches,
            key = { it.lowercase() }
        ) { keyword ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(keyword) }
                    .padding(start = 16.dp, end = 8.dp, top = 10.dp, bottom = 10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_history),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = keyword,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = { onRemoveClick(keyword) },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Remove $keyword",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ActiveFilterChipsRow(
    chips: List<ActiveFilterChip>,
    onRemoveChip: (ActiveFilterChip) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = chips,
            key = { "${it::class.simpleName}_${it.label}" }
        ) { chip ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(BrandGradient)
                    .clickable { onRemoveChip(chip) }
                    .padding(start = 14.dp, end = 10.dp, top = 7.dp, bottom = 7.dp)
            ) {
                Text(
                    text = chip.label,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = White
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove ${chip.label}",
                    tint = White,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

private sealed interface SearchBodyTransitionState {
    data object Idle : SearchBodyTransitionState
    data object Loading : SearchBodyTransitionState
    data object Error : SearchBodyTransitionState
    data object NotFound : SearchBodyTransitionState
    data class MovieGrid(
        val movies: List<Movie>,
        val filterRevision: Int
    ) : SearchBodyTransitionState
}

@Composable
private fun SearchResultBody(
    resultState: SearchResultState,
    filterRevision: Int,
    onMovieClick: (Int) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val transitionState = when (resultState) {
        SearchResultState.Idle -> SearchBodyTransitionState.Idle
        SearchResultState.Loading -> SearchBodyTransitionState.Loading
        SearchResultState.Error -> SearchBodyTransitionState.Error
        is SearchResultState.Success -> if (resultState.movies.isEmpty()) {
            SearchBodyTransitionState.NotFound
        } else {
            SearchBodyTransitionState.MovieGrid(
                movies = resultState.movies,
                filterRevision = filterRevision
            )
        }
    }

    Crossfade(
        targetState = transitionState,
        animationSpec = tween(durationMillis = RESULTS_CROSSFADE_ANIM_MS),
        modifier = modifier,
        label = "search_results_crossfade"
    ) { state ->
        when (state) {
            SearchBodyTransitionState.Idle -> {
                Box(modifier = Modifier.fillMaxSize())
            }

            SearchBodyTransitionState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(color = BrandPrimary)
                }
            }

            SearchBodyTransitionState.Error -> {
                SearchMessageState(
                    title = "Search Failed",
                    subtitle = "Could not load search results. Please check your connection and try again.",
                    actionLabel = "Retry",
                    onActionClick = onRetry,
                    modifier = Modifier.fillMaxSize()
                )
            }

            SearchBodyTransitionState.NotFound -> {
                SearchMessageState(
                    title = "Not Found",
                    subtitle = "Sorry, the keyword you entered could not be found. Try to check again or search with other keywords.",
                    modifier = Modifier.fillMaxSize()
                )
            }

            is SearchBodyTransitionState.MovieGrid -> {
                val gridState = rememberLazyGridState()
                SearchMovieGrid(
                    movies = state.movies,
                    gridState = gridState,
                    onMovieClick = onMovieClick,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun SearchMovieGrid(
    movies: List<Movie>,
    gridState: LazyGridState,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        state = gridState,
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(
            items = movies,
            key = { it.id }
        ) { movie ->
            MovieItem(
                movie = movie,
                modifier = Modifier.fillMaxWidth(),
                isBackdrop = false,
                titleColor = MaterialTheme.colorScheme.onBackground,
                onNavigateToMovieDetail = onMovieClick
            )
        }
    }
}

@Composable
private fun SearchMessageState(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(horizontal = 32.dp)
    ) {
        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        if (actionLabel != null && onActionClick != null) {
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(BrandGradient)
                    .clickable(onClick = onActionClick)
                    .padding(horizontal = 24.dp, vertical = 10.dp)
            ) {
                Text(
                    text = actionLabel,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
            }
        }
    }
}

private const val FILTER_ICON_ANIM_MS = 250
private const val RESULTS_CROSSFADE_ANIM_MS = 250

@Preview(showBackground = true)
@Composable
private fun SearchScreenRecentLightPreview() {
    AVMovieTheme(darkTheme = false) {
        SearchScreen(
            query = "",
            uiState = SearchUiState(
                recentSearches = listOf("Avatar", "Interstellar", "Inception", "Dune")
            ),
            onQueryChange = {},
            onSearchSubmit = {},
            onClearQuery = {},
            onRecentSearchClick = {},
            onRemoveRecentSearch = {},
            onApplyFilter = {},
            onRemoveFilterChip = {},
            onRetry = {},
            onBackClick = {},
            onMovieClick = {},
            autoFocus = false
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenResultsDarkPreview() {
    AVMovieTheme(darkTheme = true) {
        SearchScreen(
            query = "Avatar",
            uiState = SearchUiState(
                resultState = SearchResultState.Success(MODEL_POPULAR_MOVIES),
                activeFilterChips = listOf(
                    ActiveFilterChip.GenreChip(28, "Action"),
                    ActiveFilterChip.YearChip(2022, "2022"),
                    ActiveFilterChip.SortChip(SearchSortOption.POPULARITY, "Sort: Popularity")
                ),
                showFilterIcon = true
            ),
            onQueryChange = {},
            onSearchSubmit = {},
            onClearQuery = {},
            onRecentSearchClick = {},
            onRemoveRecentSearch = {},
            onApplyFilter = {},
            onRemoveFilterChip = {},
            onRetry = {},
            onBackClick = {},
            onMovieClick = {},
            autoFocus = false
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenNotFoundDarkPreview() {
    AVMovieTheme(darkTheme = true) {
        SearchScreen(
            query = "Avatat",
            uiState = SearchUiState(
                resultState = SearchResultState.Success(emptyList()),
                showFilterIcon = false
            ),
            onQueryChange = {},
            onSearchSubmit = {},
            onClearQuery = {},
            onRecentSearchClick = {},
            onRemoveRecentSearch = {},
            onApplyFilter = {},
            onRemoveFilterChip = {},
            onRetry = {},
            onBackClick = {},
            onMovieClick = {},
            autoFocus = false
        )
    }
}
