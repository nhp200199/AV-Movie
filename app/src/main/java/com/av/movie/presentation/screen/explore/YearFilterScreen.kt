package com.av.movie.presentation.screen.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.av.movie.ui.theme.Blue90
import com.av.movie.ui.theme.Cyan90
import com.av.movie.ui.theme.Grey10
import com.av.movie.ui.theme.LightGrey50

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YearFilterScreen(
    availableYear: List<Int>,
    selectedYear: Int?,
    onYearSelected: (Int) -> Unit,
    onReset: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Grey10)
    ) {
        CenterAlignedTopAppBar(
            title = { Text(text = "Year") },
            navigationIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Close"
                )
            },
            actions = {
                TextButton(
                    onClick = onReset,
                    enabled = selectedYear != null,
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
                actionIconContentColor = Color.White
            )
        )

        YearSelection(
            availableYear = availableYear,
            selectedYear = selectedYear,
            onYearSelected = onYearSelected,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun YearSelection(
    availableYear: List<Int>,
    selectedYear: Int?,
    onYearSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val textStyle = TextStyle(
        fontSize = 14.sp
    )
    val brush = Brush.linearGradient(
        colors = listOf(
            Blue90,
            Cyan90
        )
    )


    Column(modifier = modifier) {
        availableYear.forEach { year ->
            ListItem(
                headlineContent = {
                    Text(
                        text = year.toString(),
                        style = if (year == selectedYear) textStyle.copy(brush = brush)
                            else textStyle.copy(color = Color.White)
                    )
                },
                trailingContent = {
                    if (year == selectedYear)
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Item checked",
                            modifier = Modifier
                                .graphicsLayer(alpha = 0.99f)
                                .drawWithCache {
                                    onDrawWithContent {
                                        drawContent()
                                        drawRect(brush, blendMode = BlendMode.SrcAtop)
                                    }
                                },
                        )
                },
                colors = ListItemDefaults.colors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier.clickable {
                    onYearSelected(year)
                }
            )

            HorizontalDivider()
        }
    }
}

@Preview
@Composable
fun YearFilterScreenPreview() {
    var selectedYear: Int? by remember {
        mutableStateOf(null)
    }

    YearFilterScreen(
        availableYear = listOf(2023, 2022, 2021, 2020),
        selectedYear = selectedYear,
        onYearSelected = { selectedYear = it },
        onReset = { selectedYear = null }
    )
}