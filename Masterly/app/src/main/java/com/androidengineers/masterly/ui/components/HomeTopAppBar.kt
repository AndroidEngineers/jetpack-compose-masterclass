package com.androidengineers.masterly.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.androidengineers.masterly.R

@Preview
@Composable
fun HomeTopAppBarPreview() {
    HomeTopAppBar()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    onAnalyticsClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onProClick: () -> Unit = {}
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF121212)
        ),
        title = {
            Column {
                Text(
                    text = "Masterly",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.White
                )
                Text(
                    text = "Track your journey to mastery",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFB0B0B6),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        },
        actions = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onAnalyticsClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.chart_column),
                        contentDescription = "Analytics",
                        modifier = Modifier.size(20.dp)
                    )
                }

                IconButton(
                    onClick = onSettingsClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.settings),
                        contentDescription = "Settings",
                        modifier = Modifier.size(20.dp)
                    )
                }

                IconButton(
                    onClick = onProClick,
                    modifier = Modifier.size(40.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.crown),
                        contentDescription = "Pro",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    )
}
