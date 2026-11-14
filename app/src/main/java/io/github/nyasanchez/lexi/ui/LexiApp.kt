package io.github.nyasanchez.lexi.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.nyasanchez.lexi.R
import io.github.nyasanchez.lexi.data.vocabulary.VocabularyLevel
import io.github.nyasanchez.lexi.domain.review.ReviewItem

private const val PRACTICE_ROUTE = "practice/{mode}/{level}"
private const val REVIEW_PRACTICE_ROUTE = "review/{mode}/{entryId}"

private fun reviewPracticeRoute(item: ReviewItem): String =
    "review/${PracticeMode.forAnswerLanguage(item.answerLanguage).id}/${item.entry.id}"

private enum class MainDestination(
    val route: String,
    @param:StringRes val label: Int,
    @param:DrawableRes val icon: Int,
) {
    HOME("home", R.string.nav_home, R.drawable.ic_home),
    REVIEW("review", R.string.nav_review, R.drawable.ic_review),
    PROGRESS("progress", R.string.nav_progress, R.drawable.ic_progress),
    CONTRIBUTORS("contributors", R.string.nav_contributors, R.drawable.ic_person)
}

@Composable
fun LexiApp() {
    val progressModel: ProgressViewModel = viewModel()
    val progress by progressModel.progress.collectAsStateWithLifecycle()
    val currentSave by progressModel.save.collectAsStateWithLifecycle()
    val currentStreakState by progressModel.streak.collectAsStateWithLifecycle()
    val levelStatuses by progressModel.levels.collectAsStateWithLifecycle()
    val reviewItems by progressModel.review.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: MainDestination.HOME.route
    var selectedModeId by rememberSaveable { mutableStateOf(PracticeMode.ENGLISH.id) }
    val selectedMode = PracticeMode.fromId(selectedModeId)
    var selectedLevelName by rememberSaveable { mutableStateOf(VocabularyLevel.BASIC.name) }
    val levels = levelStatuses?.get(selectedMode.answerLanguage).orEmpty()
    // Si el nivel elegido sigue bloqueado, se practica el Básico, que siempre está disponible.
    val selectedLevel = VocabularyLevel.entries.firstOrNull { it.name == selectedLevelName }
        ?.takeUnless { chosen -> levels.firstOrNull { it.level == chosen }?.unlocked == false }
        ?: VocabularyLevel.BASIC

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
            modifier = Modifier.fillMaxSize().padding(innerPadding).consumeWindowInsets(innerPadding),
        ) {
            composable(MainDestination.HOME.route) {
                HomeScreen(
                    selectedMode = selectedMode,
                    selectedLevel = selectedLevel,
                    levels = levels,
                    streak = currentStreakState.count,
                    completedToday = currentStreakState.completedToday,
                    onModeSelected = { selectedModeId = it.id },
                    onLevelSelected = { selectedLevelName = it.name },
                    onStart = {
                        navController.navigate("practice/${selectedMode.id}/${selectedLevel.name}") {
                            launchSingleTop = true
                        }
                    },
                )
            }
            composable(MainDestination.REVIEW.route) {
                ReviewScreen(
                    pending = reviewItems,
                    onRetryWord = { item ->
                        navController.navigate(reviewPracticeRoute(item)) { launchSingleTop = true }
                    },
                    onGoHome = ::goHome,
                )
            }
            composable(MainDestination.PROGRESS.route) {
                ProgressScreen(state = progress, streak = currentStreakState.count, onRetry = progressModel::reload, onGoHome = ::goHome)
            }
            composable(MainDestination.CONTRIBUTORS.route) {
                ContributorsScreen()
            }
            composable(
                route = REVIEW_PRACTICE_ROUTE,
                arguments = listOf(
                    navArgument("mode") { type = NavType.StringType },
                    navArgument("entryId") { type = NavType.StringType },
                ),
            ) { entry ->
                val reviewMode = PracticeMode.fromId(entry.arguments?.getString("mode"))
                val reviewEntryId = entry.arguments?.getString("entryId").orEmpty()
                ReviewPracticeScreen(
                    mode = reviewMode,
                    entryId = reviewEntryId,
                    onNextWord = {
                        // Otra palabra pendiente, si la hay; si no, se vuelve a la lista de Repaso.
                        val next = reviewItems.orEmpty().firstOrNull {
                            !(it.entry.id == reviewEntryId && it.answerLanguage == reviewMode.answerLanguage)
                        }
                        if (next == null) {
                            navController.popBackStack()
                        } else {
                            navController.navigate(reviewPracticeRoute(next)) {
                                popUpTo(REVIEW_PRACTICE_ROUTE) { inclusive = true }
                            }
                        }
                    },
                    onGoBack = { navController.popBackStack() },
                    saveState = currentSave,
                    onSave = progressModel::save,
                )
            }
            composable(
                route = PRACTICE_ROUTE,
                arguments = listOf(
                    navArgument("mode") { type = NavType.StringType },
                    navArgument("level") { type = NavType.StringType },
                ),
            ) { entry ->
                val level = VocabularyLevel.entries
                    .firstOrNull { it.name == entry.arguments?.getString("level") } ?: VocabularyLevel.BASIC
                val practiceMode = PracticeMode.fromId(entry.arguments?.getString("mode"))
                PracticeScreen(
                    mode = practiceMode,
                    level = level,
                    levels = levelStatuses?.get(practiceMode.answerLanguage).orEmpty(),
                    onGoBack = { navController.popBackStack() },
                    saveState = currentSave,
                    onSave = progressModel::save,
                    onSwitchLevel = { target ->
                        // Reemplaza la práctica actual para que «←» vuelva a Inicio y no al nivel anterior.
                        selectedLevelName = target.name
                        navController.navigate("practice/${practiceMode.id}/${target.name}") {
                            popUpTo(PRACTICE_ROUTE) { inclusive = true }
                        }
                    },
                )
            }
        }
    }
}
