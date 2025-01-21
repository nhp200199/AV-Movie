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
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.av.movie.presentation.navigation.CategoryDetail
import com.av.movie.presentation.navigation.Explore
import com.av.movie.presentation.navigation.Favourites
import com.av.movie.presentation.navigation.Home
import com.av.movie.presentation.navigation.MovieDetail
import com.av.movie.presentation.navigation.Nested
import com.av.movie.presentation.navigation.Profile
import com.av.movie.presentation.screen.categoryDetail.CategoryDetailScreen
import com.av.movie.presentation.screen.home.HomeScreen
import com.av.movie.presentation.screen.home.MyHomeScreen
import com.av.movie.presentation.screen.movieDetail.MovieDetailScreen
import com.av.movie.ui.theme.Blue90
import com.av.movie.ui.theme.Cyan90
import com.av.movie.ui.theme.Grey10
import com.google.accompanist.systemuicontroller.rememberSystemUiController

data class TopLevelRoute<T: Any>(
    val name: String,
    val route: T,
    val icon: ImageVector
)

val topLevelScreens = listOf(
    TopLevelRoute("Home", Nested, Icons.Filled.Home),
    TopLevelRoute("Explore", Explore, Icons.Filled.Search),
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
                    MyHomeScreen(
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
            composable<Explore> {
                HomeScreen(navController = navHostController)
            }
            composable<Favourites> { HomeScreen(navController = navHostController) }
            composable<Profile> { HomeScreen(navController = navHostController) }

            composable<MovieDetail> {  backStackEntry ->
                val movieDetail = backStackEntry.toRoute<MovieDetail>()
                MovieDetailScreen(movieDetail.id)
            }
        }
    }
}