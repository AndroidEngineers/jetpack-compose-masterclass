package com.androidengineers.masterly.ui.screens.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.androidengineers.masterly.R

@Composable
fun TimerScreen(timerViewModel: TimerViewModel = hiltViewModel(),
                skillName: String) {
    val time by timerViewModel.time.collectAsState()
    val isRunning by timerViewModel.isRunning.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1B1F))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = skillName,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "Practice Session",
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            )
        }

        Text(
            text = time,
            style = TextStyle(
                color = Color(0xFFD0BCFF),
                fontSize = 80.sp,
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Color(0xFFD0BCFF),
                    offset = Offset(0f, 0f),
                    blurRadius = 16f
                )
            )
        )

        Row(
            modifier = Modifier.padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(
                onClick = { timerViewModel.resetTimer() },
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color(0xFF9370DB), CircleShape)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_replay_24),
                    contentDescription = "Reset",
                    tint = Color(0xFFD0BCFF),
                    modifier = Modifier.size(32.dp)
                )
            }
            IconButton(
                onClick = { timerViewModel.toggleTimer() },
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color(0xFF9370DB), CircleShape)
            ) {
                Icon(
                    painter = if (isRunning) painterResource(R.drawable.outline_airwave_24) else painterResource(R.drawable.outline_play_arrow_24),
                    contentDescription = if (isRunning) "Pause" else "Play",
                    tint = Color(0xFFD0BCFF),
                    modifier = Modifier.size(48.dp)
                )
            }
            IconButton(
                onClick = { timerViewModel.stopTimer() },
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color(0xFF9370DB), CircleShape)
            ) {
                Icon(
                    painter = painterResource(R.drawable.outline_stop_circle_24),
                    contentDescription = "Stop",
                    tint = Color(0xFFD0BCFF),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
