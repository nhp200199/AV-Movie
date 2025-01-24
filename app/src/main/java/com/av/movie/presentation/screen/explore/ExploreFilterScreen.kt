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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kotlin.reflect.KProperty

enum class ExploreFilters(
    private val genre: String
) {
    GENRE("Genre"),
    COUNTRY("Country"),
    YEAR("Year")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreFilterScreen(
    onBackClick: () -> Unit,
    onNavigateToFilterScreen: (ExploreFilters) -> Unit
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
                Text(text = "Reset")
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Grey10,
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        SortSection(modifier = Modifier.padding(horizontal = 8.dp))

        Spacer(modifier = Modifier.height(16.dp))

        FilterSection(
            modifier = Modifier.padding(horizontal = 8.dp),
            onNavigateToFilterScreen = onNavigateToFilterScreen
        )
    }
}

@Composable
fun SortSection(
    modifier: Modifier = Modifier
) {
    val sortOptions = listOf("Popular", "New", "Rating IMDB")
    var selectedOption by remember<MutableState<String?>> {
        mutableStateOf(null)
    }

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
                    selected = selectedOption === it,
                    onClick = { selectedOption = it },
                    label = { Text(text = it) },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = LightGrey10,
                        selectedContainerColor = LightGrey30,
                        selectedLabelColor = Color.White,
                        labelColor = Color.White,
                    ),
                    shape = RoundedCornerShape(50),
                    border = if (selectedOption == it) {
                        BorderStroke(width = 1.dp, brush = brush)
                    } else null
                )
            }
        }
    }
}

@Composable
fun FilterSection(
    modifier: Modifier = Modifier,
    onNavigateToFilterScreen: (ExploreFilters) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = "Filters",
            style = TextStyle(
                color = Color.White,
                fontSize = 14.sp
            )
        )

        ExploreFilters.entries.toTypedArray().forEach {
            ListItem(
                headlineContent = {
                    Text(
                        text = it.name,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp

                        )
                    )
                },
                trailingContent = {
                    Text(
                        text = "See all",
                        style = TextStyle(
                            color = Color.White,
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

@Preview
@Composable
fun ExploreFilterScreenPreview() {
    ExploreFilterScreen(
        onBackClick = {},
        onNavigateToFilterScreen = {}
    )
}