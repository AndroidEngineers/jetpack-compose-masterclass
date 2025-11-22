package com.androidengineers.masterly.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.androidengineers.masterly.ui.screens.home.HomeScreen
import com.androidengineers.masterly.ui.screens.settings.SettingsScreen
import com.androidengineers.masterly.ui.screens.timer.TimerScreen

@Composable
fun AppNavHost(modifier: Modifier, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home",
            ) {
            HomeScreen(modifier) {
               navController.navigate("timer/$it")
            }
        }

        composable("settings") {
            SettingsScreen(modifier)
        }

        composable(
            route = "timer/{skillName}"
        ) { backStackEntry ->
            val skillName = backStackEntry.arguments?.getString("skillName") ?: "default_value"
            TimerScreen(skillName = skillName)
        }
    }
}