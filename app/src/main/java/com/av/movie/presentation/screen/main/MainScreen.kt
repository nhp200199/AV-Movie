package com.av.movie.presentation.screen.main

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.av.avmovie.R
import com.av.movie.presentation.screen.home.HomeScreen
import com.av.movie.ui.theme.AVMovieTheme
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
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry
                        by navHostController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                topLevelScreens.forEach { route ->
                    BottomNavigationItem(
                        selected
                        = currentDestination?.hierarchy?.any { it.route == route.route } == true,
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
                            Icon(route.icon, contentDescription = route.name)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navHostController, startDestination = "home", Modifier.padding(innerPadding)) {
            composable("home") { HomeScreen(navController = navHostController) }
            composable("explore") { HomeScreen(navController = navHostController) }
            composable("tv") { HomeScreen(navController = navHostController) }
            composable("favourites") { HomeScreen(navController = navHostController) }
            composable("account") { HomeScreen(navController = navHostController) }
        }
    }
}