package io.github.nyasanchez.lexi.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.nyasanchez.lexi.R

private enum class MainDestination(
    val route: String,
    @param:StringRes val label: Int,
    @param:DrawableRes val icon: Int,
) {
    HOME("home", R.string.nav_home, R.drawable.ic_home),
    REVIEW("review", R.string.nav_review, R.drawable.ic_book),
    PROGRESS("progress", R.string.nav_progress, R.drawable.ic_progress),
}

@Composable
fun LexiApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: MainDestination.HOME.route
    var selectedModeId by rememberSaveable { mutableStateOf(PracticeMode.ENGLISH.id) }
    val selectedMode = PracticeMode.fromId(selectedModeId)

    fun goHome() {
        navController.popBackStack(MainDestination.HOME.route, inclusive = false)
    }

    Scaffold(
        bottomBar = {
            if (MainDestination.entries.any { it.route == currentRoute }) {
                Column {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        tonalElevation = 0.dp,
                    ) {
                        MainDestination.entries.forEach { destination ->
                            NavigationBarItem(
                                selected = currentRoute == destination.route,
                                onClick = {
                                    navController.navigate(destination.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = { Icon(painterResource(destination.icon), contentDescription = null) },
                                label = { Text(stringResource(destination.label)) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                ),
                            )
                        }
                    }
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MainDestination.HOME.route,
            modifier = Modifier.fillMaxSize().padding(innerPadding),
        ) {
            composable(MainDestination.HOME.route) {
                HomeScreen(
                    selectedMode = selectedMode,
                    onModeSelected = { selectedModeId = it.id },
                    onStart = {
                        navController.navigate("practice/${selectedMode.id}") {
                            launchSingleTop = true
                        }
                    },
                )
            }
            composable(MainDestination.REVIEW.route) {
                ReviewScreen(onGoHome = ::goHome)
            }
            composable(MainDestination.PROGRESS.route) {
                ProgressScreen(onGoHome = ::goHome)
            }
            composable(
                route = "practice/{mode}",
                arguments = listOf(navArgument("mode") { type = NavType.StringType }),
            ) { entry ->
                PracticeEmptyScreen(
                    mode = PracticeMode.fromId(entry.arguments?.getString("mode")),
                    onGoBack = { navController.popBackStack() },
                )
            }
        }
    }
}
