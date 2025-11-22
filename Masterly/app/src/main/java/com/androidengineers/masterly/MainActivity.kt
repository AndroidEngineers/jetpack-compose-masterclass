package com.androidengineers.masterly

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.androidengineers.masterly.ui.navigation.AppNavHost
import com.androidengineers.masterly.ui.components.HomeFloatingActionButton
import com.androidengineers.masterly.ui.components.HomeTopAppBar
import com.androidengineers.masterly.ui.screens.home.HomeScreen
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

            MasterlyTheme {
                Scaffold(
                    topBar = {
                        HomeTopAppBar(
                            onAnalyticsClick = {},
                            onSettingsClick = {
                                if(navController.currentDestination?.route != "settings")
                                    navController.navigate("settings")

                            },
                            onProClick = {}
                        )
                    },
                    containerColor = Color(0xFF121212),
                    floatingActionButton = {
                        if (currentDestination?.route == "home") {
                            HomeFloatingActionButton {
                                showQuickLogDialog = true
                            }
                        }
                    }
                ) { padding ->
                    AppNavHost(modifier = Modifier.padding(padding), navController)

                    if (showQuickLogDialog) {
                        QuickLogDialog(
                            skills = listOf("Android", "Kotlin", "Jetpack Compose"),
                            onDismissRequest = { showQuickLogDialog = false },
                            onSave = { _, _, _ ->}
                        )
                    }
                }
            }
        }
    }
}