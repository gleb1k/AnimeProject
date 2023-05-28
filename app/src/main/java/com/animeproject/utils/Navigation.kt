package com.animeproject.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.animeproject.ui.screen.anime.AnimeScreen
import com.animeproject.ui.screen.character.CharacterScreen
import com.animeproject.ui.screen.base.BaseScreen
import com.animeproject.ui.screen.search.SearchScreen
import com.animeproject.ui.screen.settings.SettingsScreen


@Composable
fun CustomNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Screen = Screen.Search
) {
    val bottomScreens = listOf(
        Screen.Settings,
        Screen.Search,
        Screen.Base,
    )
    Scaffold(
        bottomBar = {
            BottomNavigation(
//                backgroundColor = ItisTheme.colors.tintColor
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                bottomScreens.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon!!, contentDescription = null) },
                        label = { Text(stringResource(screen.name!!)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = startDestination.route,
            Modifier.padding(innerPadding),
        ) {
            composable(Screen.Settings.route) { SettingsScreen() }
            composable(Screen.Search.route) { SearchScreen(navController) }
            composable(Screen.Base.route) { BaseScreen() }

            composable(
                Screen.Anime.route,
                arguments = listOf(navArgument("animeId") { type = NavType.IntType })
            ) {
                AnimeScreen(it.arguments?.getInt("animeId"))
            }
            composable(Screen.Character.route,
                arguments = listOf(navArgument("characterId") { type = NavType.IntType })) {
                CharacterScreen(it.arguments?.getInt("characterId"))
            }

        }
    }
}
