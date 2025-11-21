package com.androidengineers.masterly.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SettingsScreen(modifier: Modifier) {
    Column(modifier = modifier) {
        Text("General Settings", color = Color.White)
    }
}