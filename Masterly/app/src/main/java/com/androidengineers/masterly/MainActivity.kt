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


data class AppSpacing(
    val small: Dp = 4.dp,
    val medium: Dp = 8.dp,
    val large: Dp = 16.dp
)

interface Analytics {
    fun logEvent(name: String, params: Map<String, Any?> = emptyMap())
}

class FirebaseAnalyticsImpl(
    private val firebaseAnalytics: com.google.firebase.analytics.FirebaseAnalytics
) : Analytics {

    override fun logEvent(name: String, params: Map<String, Any?>) {
        val bundle = Bundle().apply {
            params.forEach { (key, value) ->
                when (value) {
                    is String -> putString(key, value)
                    is Int -> putInt(key, value)
                    is Long -> putLong(key, value)
                    is Double -> putDouble(key, value)
                    is Boolean -> putBoolean(key, value)
                }
            }
        }
        firebaseAnalytics.logEvent(name, bundle)
    }
}

val LocalAnalytics = staticCompositionLocalOf<Analytics> {
    error("No Analytics provided")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var analyticsImpl: Analytics

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val firebaseAnalytics = com.google.firebase.analytics.FirebaseAnalytics.getInstance(this)
        analyticsImpl = FirebaseAnalyticsImpl(firebaseAnalytics)

        setContent {

            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            var showQuickLogDialog by remember { mutableStateOf(false) }
            val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
            val snackbarHostState = remember { SnackbarHostState() }

            var isDarkTheme by remember { mutableStateOf(false) }

            CompositionLocalProvider(
                LocalAnalytics provides analyticsImpl
            ) {
                MasterlyTheme(darkTheme = isDarkTheme) {
                    Scaffold(
                        topBar = {
                            HomeTopAppBar(
                                onAnalyticsClick = {},
                                onSettingsClick = {
                                    if (navController.currentDestination?.route != "settings")
                                        navController.navigate("settings")

                                },
                                onProClick = {

                                }
                            )
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
                        /*AppNavHost(modifier = Modifier.padding(padding), navController, homeScreenViewModel)
                       */
                        /*Button(
                            modifier = Modifier
                                .padding(padding),
                            onClick = { isDarkTheme = !isDarkTheme },
                            colors= ButtonDefaults.buttonColors(
                                containerColor = if(isDarkTheme) Color.DarkGray else Color.Red
                            ),
                            elevation = ButtonDefaults.elevatedButtonElevation(
                                pressedElevation = 20.dp
                            )
                        ) {
                            Text(
                                if (isDarkTheme) "Switch to Light Mode" else "Switch to Dark Mode"
                            )
                        }*/

                        AnimationDisplayScreen(Modifier.padding(padding))

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
                                onAddNewSkill = { name, goalMinutes ->
                                    homeScreenViewModel.onEvent(
                                        DashboardEvent.AddSkill(
                                            name = name,
                                            goalMinutes = goalMinutes
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
}