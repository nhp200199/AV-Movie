package com.av.movie.presentation.screen.main

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.av.movie.data.api.model.Genre
import com.av.movie.dataTest.ALL_GENRES
import com.av.movie.presentation.navigation.CategoryDetail
import com.av.movie.presentation.navigation.CountryFilter
import com.av.movie.presentation.navigation.Explore
import com.av.movie.presentation.navigation.ExploreFilter
import com.av.movie.presentation.navigation.ExploreNested
import com.av.movie.presentation.navigation.Favourites
import com.av.movie.presentation.navigation.GenreFilter
import com.av.movie.presentation.navigation.Home
import com.av.movie.presentation.navigation.Nested
import com.av.movie.presentation.navigation.Profile
import com.av.movie.presentation.navigation.YearFilter
import com.av.movie.presentation.screen.categoryDetail.CategoryDetailScreen
import com.av.movie.presentation.screen.explore.CountryFilterScreen
import com.av.movie.presentation.screen.explore.ExploreFilterScreen
import com.av.movie.presentation.screen.explore.ExploreScreen
import com.av.movie.presentation.screen.explore.ExploreScreenVM
import com.av.movie.presentation.screen.explore.ExploreViewModel
import com.av.movie.presentation.screen.explore.Filter
import com.av.movie.presentation.screen.explore.GenreFilterScreen
import com.av.movie.presentation.screen.explore.YearFilterScreen
import com.av.movie.presentation.screen.home.HomeScreen
import com.av.movie.presentation.screen.home.MyHomeScreen
import com.av.movie.presentation.screen.home.MyHomeScreenVM
import com.av.movie.presentation.screen.home.viewmodel.HomeViewModel
import com.av.movie.ui.theme.Blue90
import com.av.movie.ui.theme.Cyan90
import com.av.movie.ui.theme.Grey10
import com.av.movie.utils.sharedViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

data class TopLevelRoute<T: Any>(
    val name: String,
    val route: T,
    val icon: ImageVector
)

val topLevelScreens = listOf(
    TopLevelRoute("Home", Nested, Icons.Filled.Home),
    TopLevelRoute("Explore", ExploreNested, Icons.Filled.Search),
    TopLevelRoute("Favourites", Favourites, Icons.Filled.Favorite),
    TopLevelRoute("Profile", Profile, Icons.Filled.AccountCircle)
)

@Composable
fun MainScreen(
    onNavigateMovieDetail: (Int) -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colorScheme.primary

    SideEffect {
        systemUiController.setSystemBarsColor(color = systemBarColor)
    }

    val navHostController = rememberNavController()

    Scaffold(
        containerColor = Grey10,
        bottomBar = {
            BottomNavigation(
                backgroundColor = Grey10,
            ) {
                val navBackStackEntry
                        by navHostController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                val currentRoute = currentDestination?.route

                Log.d("TAG", "MainScreen: $currentRoute")

                topLevelScreens.forEach { topLevelRoute ->
                    val isSelected
                        = currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) }
                            ?: false
                    BottomNavigationItem(
                        selected = isSelected,
                        onClick = {
                            navHostController.navigate(topLevelRoute.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navHostController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // re-selecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                        icon = {
                            val modifier = if (isSelected) {
                                val brush = Brush.verticalGradient(
                                    listOf(
                                        Cyan90,
                                        Blue90,
                                    )
                                )

                                Modifier
                                    .graphicsLayer(alpha = 0.99f)
                                    .drawWithCache {
                                        onDrawWithContent {
                                            drawContent()
                                            drawRect(brush, blendMode = BlendMode.SrcAtop)
                                        }
                                    }
                            } else {
                                Modifier
                            }
                            Icon(
                                topLevelRoute.icon,
                                contentDescription = topLevelRoute.name,
                                modifier = modifier
                            )
                        },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = Nested,
            Modifier.padding(innerPadding)
        ) {

            navigation<Nested>(startDestination = Home) {
                composable<Home> {
                    MyHomeScreenVM(
                        onNavigateToCategoryDetail = { category ->
                            navHostController.navigate(CategoryDetail(category))
                        },
                        onNavigateToMovieDetail = onNavigateMovieDetail
                    )
                }

                composable<CategoryDetail> { backStackEntry ->
                    val categoryDetail = backStackEntry.toRoute<CategoryDetail>()

                    CategoryDetailScreen(name = categoryDetail.category) {
                        navHostController.navigateUp()
                    }
                }
            }

            navigation<ExploreNested>(startDestination = Explore) {
                composable<Explore> { entry ->
                    val viewModel = entry.sharedViewModel<ExploreViewModel>(navHostController)

                    ExploreScreenVM(
                        vm = viewModel,
                        onNavigateExploreFilterScreen = {
                            navHostController.navigate(ExploreFilter)
                        }
                    )
                }
                composable<ExploreFilter> { entry ->
                    val viewModel = entry.sharedViewModel<ExploreViewModel>(navHostController)
                    val filterState by viewModel.mergedSortFilterData.collectAsStateWithLifecycle()

                    ExploreFilterScreen(
                        sortFilterData = filterState,
                        onBackClick = { navHostController.navigateUp() },
                        onNavigateToFilterScreen = { filterName ->
                            when (filterName) {
                                Filter.GENRE -> navHostController.navigate(GenreFilter)
                                Filter.COUNTRY -> navHostController.navigate(CountryFilter)
                                Filter.YEAR -> navHostController.navigate(YearFilter)
                            }
                        },
                        onReset = { viewModel.resetAllSortFilter() },
                        onSortSelected = { viewModel.sortBy(it) }
                    )
                }

                composable<YearFilter> { entry ->
                    val viewModel = entry.sharedViewModel<ExploreViewModel>(navHostController)
                    val filterState by viewModel.mergedSortFilterData.collectAsStateWithLifecycle()

                    YearFilterScreen(
                        availableYear = listOf(2024, 2022, 2021, 2020),
                        selectedYear = filterState.year,
                        onYearSelected = { viewModel.filterByYear(it) },
                        onReset = { viewModel.filterByYear(null) },
                        onNavigateUp = { navHostController.navigateUp() }
                    )
                }

                composable<GenreFilter> { entry ->
                    val viewModel = entry.sharedViewModel<ExploreViewModel>(navHostController)
                    val filterState by viewModel.mergedSortFilterData.collectAsStateWithLifecycle()
                    val availableGenres by viewModel.genres.collectAsStateWithLifecycle()

                    GenreFilterScreen(
                        availableGenres = availableGenres.map { Genre(it, it.toString()) },
                        selectedGenres = filterState.genre,
                        onGenreSelected = { viewModel.toggleGenre(it.id) },
                        onReset = { viewModel.resetGenreFilter() },
                        onNavigateUp = { navHostController.navigateUp() }
                    )
                }

                composable<CountryFilter> { entry ->
                    val viewModel = entry.sharedViewModel<ExploreViewModel>(navHostController)
                    val filterState by viewModel.mergedSortFilterData.collectAsStateWithLifecycle()
                    val availableCountries by viewModel.countries.collectAsStateWithLifecycle()

                    CountryFilterScreen(
                        availableCountries = availableCountries,
                        selectedCountry = filterState.country,
                        onCountrySelected = { viewModel.filterByCountry(it) },
                        onReset = { viewModel.filterByCountry(null) },
                        onNavigateUp = { navHostController.navigateUp() }
                    )
                }
            }
            composable<Favourites> { HomeScreen(navController = navHostController) }
            composable<Profile> { HomeScreen(navController = navHostController) }
        }
    }
}