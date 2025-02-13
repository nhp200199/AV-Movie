package com.av.movie.presentation.screen.explore

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.av.movie.ui.theme.Blue90
import com.av.movie.ui.theme.Cyan90
import com.av.movie.ui.theme.Grey10
import com.av.movie.ui.theme.LightGrey10
import com.av.movie.ui.theme.LightGrey30
import com.av.movie.ui.theme.LightGrey50

enum class SortOption(
    val sortName: String
) {
    POPULAR("Popular"),
    NEW("New"),
    RATING("Rating IMDB"),
}

enum class Filter(
    val genre: String
) {
    GENRE("Genre"),
    COUNTRY("Country"),
    YEAR("Year")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreFilterScreen(
    sortFilterData: SortFilterData,
    onBackClick: () -> Unit,
    onNavigateToFilterScreen: (Filter) -> Unit,
    onReset: () -> Unit,
    onSortSelected: (SortOption) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Grey10)
    ) {
        CenterAlignedTopAppBar(
            title = { Text(text = "Filters") },
            navigationIcon = {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Close"
                    )
                }
            },
            actions = {
                TextButton(
                    onClick = onReset,
                    enabled = sortFilterData != SortFilterData.EMPTY,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.White,
                        disabledContentColor = LightGrey50
                    )
                ) {
                    Text(text = "Reset")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Grey10,
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        SortSection(
            modifier = Modifier.padding(horizontal = 8.dp),
            selectedSortOption = sortFilterData.sort,
            onSortSelected = onSortSelected
        )

        Spacer(modifier = Modifier.height(16.dp))

        FilterSection(
            filterData = sortFilterData,
            modifier = Modifier.padding(horizontal = 8.dp),
            onNavigateToFilterScreen = onNavigateToFilterScreen
        )
    }
}

@Composable
fun SortSection(
    modifier: Modifier = Modifier,
    sortOptions: List<SortOption> = SortOption.entries.toList(),
    selectedSortOption: SortOption? = null,
    onSortSelected: (SortOption) -> Unit
) {
    val brush = Brush.horizontalGradient(
        colors = listOf(
            Blue90,
            Cyan90
        )
    )


    Column(modifier = modifier) {
        Text(
            text = "Sort",
            style = TextStyle(
                color = Color.White,
                fontSize = 14.sp
            )
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            sortOptions.forEach {
                FilterChip(
                    selected = selectedSortOption == it,
                    onClick = { onSortSelected(it) },
                    label = { Text(text = it.sortName) },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = LightGrey10,
                        selectedContainerColor = LightGrey30,
                        selectedLabelColor = Color.White,
                        labelColor = Color.White,
                    ),
                    shape = RoundedCornerShape(50),
                    border = if (selectedSortOption == it) {
                        BorderStroke(width = 1.dp, brush = brush)
                    } else null
                )
            }
        }
    }
}

@Composable
fun FilterSection(
    filterData: SortFilterData,
    modifier: Modifier = Modifier,
    onNavigateToFilterScreen: (Filter) -> Unit
) {
    val brush = Brush.horizontalGradient(
        colors = listOf(
            Blue90,
            Cyan90
        )
    )

    Column(modifier = modifier) {
        Text(
            text = "Filters",
            style = TextStyle(
                color = Color.White,
                fontSize = 14.sp
            )
        )

        Filter.entries.toTypedArray().forEach {
            ListItem(
                headlineContent = {
                    Text(
                        text = it.genre,
                        style = TextStyle(
                          color = Color.White,
                            fontSize = 14.sp

                        )
                    )
                },
                trailingContent = {
                    Text(
                        text = getFilterDescription(it, filterData),
                        style = TextStyle(
                            brush = brush,
                            fontSize = 12.sp
                        )
                    )
                },
                colors = ListItemDefaults.colors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .clickable {
                        onNavigateToFilterScreen(it)
                    }
            )

            HorizontalDivider()
        }
    }
}

private fun getFilterDescription(filter: Filter, filterData: SortFilterData): String {
    return when (filter) {
        Filter.GENRE -> getGenreDescription(filterData.genre?.map { it.name })
        Filter.COUNTRY -> filterData.country ?: "All"
        Filter.YEAR -> filterData.year?.toString() ?: "All"
    }
}

private fun getGenreDescription(genres: List<String>?): String {
    return if (genres.isNullOrEmpty()) {
        "All"
    } else {
        if (genres.size > 1) {
            genres[0] + ", +${genres.size - 1}"
        } else {
            genres[0]
        }
    }
}

@Preview
@Composable
fun ExploreFilterScreenPreview() {
    ExploreFilterScreen(
        sortFilterData = SortFilterData(),
        onBackClick = {},
        onNavigateToFilterScreen = {},
        onReset = {},
        onSortSelected = {}
    )
}