package com.av.movie.presentation.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.av.movie.domain.model.AvailableSearchFilters
import com.av.movie.domain.model.Genre
import com.av.movie.domain.model.LanguageOption
import com.av.movie.domain.model.SearchMovieFilter
import com.av.movie.domain.model.SearchSortOption
import com.av.movie.ui.theme.AVMovieTheme
import com.av.movie.ui.theme.BrandGradient
import com.av.movie.ui.theme.BrandPrimary
import com.av.movie.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFilterBottomSheet(
    initialFilter: SearchMovieFilter,
    availableFilters: AvailableSearchFilters,
    onDismiss: () -> Unit,
    onApply: (SearchMovieFilter) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        SearchFilterSheetContent(
            initialFilter = initialFilter,
            availableFilters = availableFilters,
            onApply = { filter ->
                onApply(filter)
                onDismiss()
            }
        )
    }
}

@Composable
internal fun SearchFilterSheetContent(
    initialFilter: SearchMovieFilter,
    availableFilters: AvailableSearchFilters,
    onApply: (SearchMovieFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    var draftFilter by remember(initialFilter) { mutableStateOf(initialFilter) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        Text(
            text = "Sort & Filter",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            if (availableFilters.genres.isNotEmpty()) {
                FilterChipSection(title = "Genre") {
                    items(availableFilters.genres, key = { it.id }) { genre ->
                        val isSelected = genre.id in draftFilter.genreIds
                        FilterOptionChip(
                            label = genre.name,
                            selected = isSelected,
                            onClick = {
                                val updated = if (isSelected) {
                                    draftFilter.genreIds - genre.id
                                } else {
                                    draftFilter.genreIds + genre.id
                                }
                                draftFilter = draftFilter.copy(genreIds = updated)
                            }
                        )
                    }
                }
            }

            if (availableFilters.languages.isNotEmpty()) {
                FilterChipSection(title = "Language") {
                    item(key = "all_languages") {
                        FilterOptionChip(
                            label = "All Languages",
                            selected = draftFilter.originalLanguage == null,
                            onClick = { draftFilter = draftFilter.copy(originalLanguage = null) }
                        )
                    }
                    items(availableFilters.languages, key = { it.code }) { language ->
                        val isSelected = draftFilter.originalLanguage.equals(language.code, ignoreCase = true)
                        FilterOptionChip(
                            label = language.displayName,
                            selected = isSelected,
                            onClick = {
                                draftFilter = draftFilter.copy(
                                    originalLanguage = if (isSelected) null else language.code
                                )
                            }
                        )
                    }
                }
            }

            if (availableFilters.releaseYears.isNotEmpty()) {
                FilterChipSection(title = "Release Year") {
                    item(key = "all_years") {
                        FilterOptionChip(
                            label = "All Years",
                            selected = draftFilter.releaseYear == null,
                            onClick = { draftFilter = draftFilter.copy(releaseYear = null) }
                        )
                    }
                    items(availableFilters.releaseYears, key = { it }) { year ->
                        val isSelected = draftFilter.releaseYear == year
                        FilterOptionChip(
                            label = year.toString(),
                            selected = isSelected,
                            onClick = {
                                draftFilter = draftFilter.copy(
                                    releaseYear = if (isSelected) null else year
                                )
                            }
                        )
                    }
                }
            }

            if (availableFilters.ratingThresholds.isNotEmpty()) {
                FilterChipSection(title = "Minimum Rating") {
                    item(key = "all_ratings") {
                        FilterOptionChip(
                            label = "All Ratings",
                            selected = draftFilter.minVoteAverage == null,
                            onClick = { draftFilter = draftFilter.copy(minVoteAverage = null) }
                        )
                    }
                    items(availableFilters.ratingThresholds, key = { it }) { threshold ->
                        val isSelected = draftFilter.minVoteAverage == threshold
                        FilterOptionChip(
                            label = formatRatingThresholdLabel(threshold),
                            selected = isSelected,
                            onClick = {
                                draftFilter = draftFilter.copy(
                                    minVoteAverage = if (isSelected) null else threshold
                                )
                            }
                        )
                    }
                }
            }

            FilterChipSection(title = "Sort By") {
                item(key = "default_sort") {
                    FilterOptionChip(
                        label = "Default",
                        selected = draftFilter.sortBy == null,
                        onClick = { draftFilter = draftFilter.copy(sortBy = null) }
                    )
                }
                items(availableFilters.sortOptions, key = { it.name }) { option ->
                    val isSelected = draftFilter.sortBy == option
                    FilterOptionChip(
                        label = option.label,
                        selected = isSelected,
                        onClick = {
                            draftFilter = draftFilter.copy(
                                sortBy = if (isSelected) null else option
                            )
                        }
                    )
                }
            }
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { draftFilter = SearchMovieFilter.EMPTY }
            ) {
                Text(
                    text = "Reset",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(50))
                    .background(BrandGradient)
                    .clickable { onApply(draftFilter) }
            ) {
                Text(
                    text = "Apply",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
            }
        }
    }
}

@Composable
private fun FilterChipSection(
    title: String,
    content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            content = content
        )
    }
}

@Composable
private fun FilterOptionChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(50)
    val baseModifier = Modifier
        .clip(shape)
        .then(
            if (selected) {
                Modifier.background(brush = BrandGradient, shape = shape)
            } else {
                Modifier
                    .background(color = Color.Transparent, shape = shape)
                    .border(width = 1.5.dp, color = BrandPrimary, shape = shape)
            }
        )
        .clickable(onClick = onClick)
        .padding(horizontal = 16.dp, vertical = 8.dp)

    Box(modifier = baseModifier) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (selected) White else BrandPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchFilterSheetLightPreview() {
    AVMovieTheme(darkTheme = false) {
        SearchFilterSheetContent(
            initialFilter = SearchMovieFilter(
                genreIds = setOf(28),
                originalLanguage = "en",
                releaseYear = 2022,
                minVoteAverage = 7.0,
                sortBy = SearchSortOption.POPULARITY
            ),
            availableFilters = AvailableSearchFilters(
                genres = listOf(Genre(28, "Action"), Genre(12, "Adventure"), Genre(878, "Sci-Fi")),
                languages = listOf(LanguageOption("en", "English"), LanguageOption("ko", "Korean")),
                releaseYears = listOf(2024, 2022, 2009),
                ratingThresholds = listOf(8.0, 7.0, 6.0)
            ),
            onApply = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1F222A)
@Composable
private fun SearchFilterSheetDarkPreview() {
    AVMovieTheme(darkTheme = true) {
        SearchFilterSheetContent(
            initialFilter = SearchMovieFilter.EMPTY,
            availableFilters = AvailableSearchFilters(
                genres = listOf(Genre(28, "Action"), Genre(12, "Adventure")),
                languages = listOf(LanguageOption("en", "English")),
                releaseYears = listOf(2024, 2022),
                ratingThresholds = listOf(8.0, 7.0)
            ),
            onApply = {}
        )
    }
}
