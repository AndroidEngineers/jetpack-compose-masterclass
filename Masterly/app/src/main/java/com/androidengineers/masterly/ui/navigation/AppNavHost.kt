package com.androidengineers.masterly.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.androidengineers.masterly.ui.AnimationDisplayScreen
import com.androidengineers.masterly.ui.screens.home.HomeScreen
import com.androidengineers.masterly.ui.screens.home.HomeScreenViewModel
import com.androidengineers.masterly.ui.screens.settings.SettingsScreen
import com.androidengineers.masterly.ui.screens.skilldetail.SkillDetailScreen
import com.androidengineers.masterly.ui.screens.timer.TimerScreen

@Composable
fun AppNavHost(
    modifier: Modifier,
    navController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home",
            ) {
            HomeScreen(modifier, homeScreenViewModel = homeScreenViewModel) {
               navController.navigate("skillDetail/$it")
            }
        }

        composable("animation") {
            AnimationDisplayScreen()
        }

        composable("settings") {
            SettingsScreen(
                modifier = modifier,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = "timer/{skillId}",
            arguments = listOf(
                navArgument("skillId") {
                    type = NavType.StringType
                    nullable = false
                },
            ),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://masterly.app/timer/{skillId}"
                }
            ),
            // Forward navigation uses → enter + exit
            // Back navigation uses → popEnter + popExit
        ) { backStackEntry ->
            // TimerScreen will now get skillId from SavedStateHandle in ViewModel
            TimerScreen(
                modifier = modifier,
                onBackClick = { navController.popBackStack() })
        }

        composable(
            route = "skillDetail/{skillId}",
            arguments = listOf(
                navArgument("skillId") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val skillId = backStackEntry.arguments?.getString("skillId") ?: ""
            SkillDetailScreen(
                modifier=modifier,
                skillId = skillId,
                onBackClick = {
                    navController.popBackStack()
                },
                onStartTimerClick = {
                    navController.navigate("timer/$skillId")
                }
            )
        }
    }
}