package com.androidengineers.masterly

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.MarqueeDefaults.Spacing
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.androidengineers.masterly.ui.AnimationDisplayScreen
import com.androidengineers.masterly.ui.navigation.AppNavHost
import com.androidengineers.masterly.ui.components.HomeFloatingActionButton
import com.androidengineers.masterly.ui.components.HomeTopAppBar
import com.androidengineers.masterly.ui.screens.home.DashboardEffect
import com.androidengineers.masterly.ui.screens.home.DashboardEvent
import com.androidengineers.masterly.ui.screens.home.HomeScreenViewModel
import com.androidengineers.masterly.ui.screens.home.QuickLogDialog
import com.androidengineers.masterly.ui.theme.MasterlyTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            var showQuickLogDialog by remember { mutableStateOf(false) }
            val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
            val snackbarHostState = remember { SnackbarHostState() }

            var isDarkTheme by remember { mutableStateOf(false) }
                MasterlyTheme(darkTheme = isDarkTheme) {
                    Scaffold(
                        topBar = {
                            if (currentDestination?.route?.startsWith("skillDetail") != true &&
                                currentDestination?.route?.startsWith("settings") != true) {
                                HomeTopAppBar(
                                    onAnalyticsClick = {},
                                    onSettingsClick = {
                                        if (navController.currentDestination?.route != "settings")
                                            navController.navigate("settings")

                                    },
                                    onProClick = {

                                    }
                                )
                            }
                        },
                        containerColor = Color(0xFF121212),
                        floatingActionButton = {
                            if (currentDestination?.route == "home") {
                                HomeFloatingActionButton {
                                    showQuickLogDialog = true
                                }
                            }
                        },
                        snackbarHost = { SnackbarHost(snackbarHostState) }
                    ) { padding ->
                        AppNavHost(modifier = Modifier.padding(padding), navController, homeScreenViewModel)
                        LaunchedEffect(Unit) {
                            homeScreenViewModel.effects.collect { effect ->
                                when (effect) {
                                    is DashboardEffect.ShowError -> {
                                        snackbarHostState.showSnackbar(effect.message)
                                    }

                                    DashboardEffect.SkillAdded -> {
                                        snackbarHostState.showSnackbar("Skill added 🎯")
                                    }
                                }
                            }
                        }

                        if (showQuickLogDialog) {
                            QuickLogDialog(
                                onDismissRequest = { showQuickLogDialog = false },
                                onAddNewSkill = { name ->
                                    homeScreenViewModel.onEvent(
                                        DashboardEvent.AddSkill(
                                            name = name,
                                            goalMinutes = 600000 // 10,000 hours
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
        
        }
    }
}