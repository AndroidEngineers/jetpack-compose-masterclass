package com.androidengineers.masterly

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.androidengineers.masterly.ui.components.HomeFloatingActionButton
import com.androidengineers.masterly.ui.components.HomeTopAppBar
import com.androidengineers.masterly.ui.screens.HomeScreen
import com.androidengineers.masterly.ui.theme.MasterlyTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MasterlyTheme {
                Scaffold(
                    topBar = {
                        HomeTopAppBar(
                            onAnalyticsClick = {},
                            onSettingsClick = {},
                            onProClick = {}
                        )
                    },
                    containerColor = Color(0xFF121212),
                    floatingActionButton = {
                        HomeFloatingActionButton() {  }
                    }
                ) { padding ->
                    HomeScreen(modifier = Modifier.padding(padding))
                }
            }
        }
    }
}