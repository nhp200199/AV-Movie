package com.av.movie.presentation.screen.main

import android.os.Bundle
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.av.movie.presentation.screen.categoryDetail.CategoryDetailScreen
import com.av.movie.presentation.screen.home.HomeScreen
import com.av.movie.presentation.screen.home.MyHomeScreen
import com.av.movie.ui.theme.Blue90
import com.av.movie.ui.theme.Cyan90
import com.av.movie.ui.theme.Grey10
import com.google.accompanist.systemuicontroller.rememberSystemUiController

sealed class TopLevelScreen(val route: String, val name: String, val icon: ImageVector) {
    object Home : TopLevelScreen("home", "Home", Icons.Filled.Home)
    object Explore : TopLevelScreen("explore", "Explore", Icons.Filled.Search)
//    object TV : TopLevelScreen("tv", "TV", painterResource(id = ))
    object Favourites : TopLevelScreen("favourites", "Favourites", Icons.Filled.Favorite)
    object Account : TopLevelScreen("account", "Account", Icons.Filled.AccountCircle)
}

val topLevelScreens = listOf(
    TopLevelScreen.Home,
    TopLevelScreen.Explore,
//    TopLevelScreen.TV,
    TopLevelScreen.Favourites,
    TopLevelScreen.Account
)

@Composable
fun MainScreen() {
    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colorScheme.primary
//    val allMovies = viewModel.latestMovie

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

                topLevelScreens.forEach { route ->
                    BottomNavigationItem(
                        selected
                            = route.route == currentRoute,
                        onClick = {
                            navHostController.navigate(route.name) {
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
                            val modifier = if (route.route == currentRoute) {
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
                                route.icon,
                                contentDescription = route.name,
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
        NavHost(navHostController, startDestination = "home", Modifier.padding(innerPadding)) {
            composable(TopLevelScreen.Home.route) {
                MyHomeScreen()
            }
            composable("explore") {
                CategoryDetailScreen(
                    name = "test",
                )
            }
            composable("tv") { HomeScreen(navController = navHostController) }
            composable("favourites") { HomeScreen(navController = navHostController) }
            composable("account") { HomeScreen(navController = navHostController) }
        }
    }
}