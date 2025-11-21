package com.androidengineers.masterly

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.androidengineers.masterly.ui.navigation.AppNavHost
import com.androidengineers.masterly.ui.components.HomeFloatingActionButton
import com.androidengineers.masterly.ui.components.HomeTopAppBar
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

            MasterlyTheme {
                Scaffold(
                    topBar = {
                        HomeTopAppBar(
                            onAnalyticsClick = {},
                            onSettingsClick = {
                                if(currentDestination?.route != "settings")
                                    navController.navigate("settings")
                            },
                            onProClick = {}
                        )
                    },
                    containerColor = Color(0xFF121212),
                    floatingActionButton = {
                        if(currentDestination?.route == "home") {
                            HomeFloatingActionButton() {}
                        }
                    }
                ) { padding ->
                    AppNavHost(modifier = Modifier.padding(padding), navController)
                }
            }
        }
    }
}