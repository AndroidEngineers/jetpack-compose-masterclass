package com.androidengineers.masterly.ui.screens.skilldetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults

@Composable
fun SkillDetailScreen(
    modifier: Modifier = Modifier,
    skillId: String,
    viewModel: SkillDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onStartTimerClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showEditDialog) {
        val currentState = uiState
        if (currentState is SkillDetailUiState.Success) {
            var newName by remember { mutableStateOf(currentState.skill.name) }
            AlertDialog(
                onDismissRequest = { showEditDialog = false },
                title = { Text("Edit Skill Name") },
                text = {
                    TextField(
                        value = newName,
                        onValueChange = { newName = it },
                        label = { Text("Skill Name") },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color(0xFFBB86FC),
                            unfocusedIndicatorColor = Color.Gray,
                            focusedLabelColor = Color(0xFFBB86FC),
                            unfocusedLabelColor = Color.Gray,
                            cursorColor = Color(0xFFBB86FC),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        )
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.updateSkillName(newName)
                        showEditDialog = false
                    }) {
                        Text("Save", color = Color(0xFFBB86FC))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEditDialog = false }) {
                        Text("Cancel", color = Color.Gray)
                    }
                },
                containerColor = Color(0xFF2C2C2C),
                titleContentColor = Color.White,
                textContentColor = Color.White
            )
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Skill") },
            text = { Text("Are you sure you want to delete this skill? This action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteSkill {
                        onBackClick()
                    }
                    showDeleteDialog = false
                }) {
                    Text("Delete", color = Color(0xFFCF6679))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel", color = Color.Gray)
                }
            },
            containerColor = Color(0xFF2C2C2C),
            titleContentColor = Color.White,
            textContentColor = Color.White
        )
    }

    when (val state = uiState) {
        is SkillDetailUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is SkillDetailUiState.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.message, color = Color.Red)
            }
        }
        is SkillDetailUiState.Success -> {
            val skill = state.skill
            val skillName = skill.name
            val totalHours = skill.millisPracticed / (1000 * 60 * 60)
            val goalHours = skill.goalMinutes / 60
            val goalMillis = skill.goalMinutes * 60 * 1000L
            val progress = if (skill.goalMinutes > 0) skill.millisPracticed.toDouble() / goalMillis.toDouble() else 0.0

            Surface(
                modifier = modifier.fillMaxSize(),
                color = Color(0xFF1E1E1E) // Dark background from image
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(scrollState)
                ) {
                    // Top Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Back", color = Color.White)
                        }

                        Row {
                            OutlinedButton(
                                onClick = { showEditDialog = true },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFBB86FC))
                            ) {
                                Text("Edit")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = { showDeleteDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCF6679)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Delete", color = Color.White)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Title
                    Text(
                        text = skillName,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Progress Section
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Progress toward mastery", color = Color.Gray, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                LinearProgressIndicator(
                                    progress = { progress.toFloat() },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(8.dp)
                                        .clip(RoundedCornerShape(4.dp)),
                                    color = Color(0xFFBB86FC),
                                    trackColor = Color(0xFF333333),
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("${"%.6f".format(progress * 100)}%", color = Color(0xFFBB86FC), fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.width(24.dp))
                        Column {
                            Text("Total hours", color = Color.Gray, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text("$totalHours", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                Text(" / $goalHours", color = Color.Gray, fontSize = 14.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Action Buttons
                    Row {
                        Button(
                            onClick = onStartTimerClick,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBB86FC)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Start Timer", color = Color.White)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        OutlinedButton(
                            onClick = { /* TODO */ },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF03DAC5))
                        ) {
                            Text("Quick Log")
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Tabs
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF2C2C2C), RoundedCornerShape(8.dp))
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val tabs = listOf("Overview", "Sessions", "Analytics", "Milestones")
                        var selectedTab by remember { mutableStateOf(0) }

                        tabs.forEachIndexed { index, title ->
                            val isSelected = selectedTab == index
                            TextButton(
                                onClick = { selectedTab = index },
                                modifier = Modifier
                                    .weight(1f)
                                    .background(
                                        if (isSelected) Color(0xFF3E3E3E) else Color.Transparent,
                                        RoundedCornerShape(6.dp)
                                    )
                            ) {
                                Text(
                                    text = title,
                                    color = if (isSelected) Color.White else Color.Gray,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Weekly Summary Chart
                    Surface(
                        color = Color(0xFF2C2C2C),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Weekly Summary", color = Color.White, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            // Simple Bar Chart Placeholder
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                val weeks = listOf(
                                    "Week 1" to 12,
                                    "Week 2" to 8,
                                    "Week 3" to 15,
                                    "Week 4" to 10
                                )
                                val maxVal = weeks.maxOf { it.second }
                                
                                weeks.forEach { (week, hours) ->
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Box(
                                            modifier = Modifier
                                                .width(40.dp)
                                                .height((100 * hours / maxVal).dp)
                                                .background(
                                                    androidx.compose.ui.graphics.Brush.verticalGradient(
                                                        colors = listOf(Color(0xFFBB86FC), Color(0xFF03DAC5))
                                                    ),
                                                    RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                                                )
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(week, color = Color.Gray, fontSize = 10.sp)
                                        Text("${hours}h", color = Color.White, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Monthly Activity Calendar
                    Surface(
                        color = Color(0xFF2C2C2C),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Monthly Activity", color = Color.White, fontWeight = FontWeight.Bold)
                                Icon(Icons.Default.DateRange, contentDescription = "Calendar", tint = Color(0xFFBB86FC))
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Simple Calendar Grid Placeholder
                            val days = listOf("S", "M", "T", "W", "T", "F", "S")
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                days.forEach { 
                                    Text(it, color = Color.Gray, fontSize = 12.sp, modifier = Modifier.width(30.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            // Mock dates
                            val dates = (1..31).toList()
                            val offset = 2 // Start on Tuesday
                            
                            Column {
                                var currentDay = 1
                                for (row in 0..4) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        for (col in 0..6) {
                                            if (row == 0 && col < offset) {
                                                Spacer(modifier = Modifier.width(30.dp))
                                            } else if (currentDay <= 31) {
                                                Text(
                                                    text = "$currentDay",
                                                    color = Color.LightGray,
                                                    fontSize = 12.sp,
                                                    modifier = Modifier.width(30.dp),
                                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                                )
                                                currentDay++
                                            } else {
                                                Spacer(modifier = Modifier.width(30.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
