package com.androidengineers.masterly.ui.screens.home

// Required imports (Material 3)
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickLogDialog(
    skills: List<String>,
    initialSkill: String? = null,
    initialHours: String = "0",
    initialMinutes: String = "0",
    initialNotes: String = "",
    onDismissRequest: () -> Unit,
    onSave: (skill: String, totalMinutes: Int, notes: String) -> Unit,
) {
    // ---- Local state for dialog fields ----
    var selectedSkill by remember { mutableStateOf(initialSkill ?: "") }
    var skillExpanded by remember { mutableStateOf(false) }

    var hoursText by remember { mutableStateOf(initialHours) }
    var minutesText by remember { mutableStateOf(initialMinutes) }
    var notesText by remember { mutableStateOf(initialNotes) }

    // Parse duration safely
    val hours = hoursText.toIntOrNull() ?: 0
    val minutes = minutesText.toIntOrNull() ?: 0
    val totalMinutes = (hours.coerceAtLeast(0) * 60) + minutes.coerceIn(0, 59)

    val isSaveEnabled = selectedSkill.isNotBlank() && totalMinutes > 0

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF111111), // card bg
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // -------- Select Skill ----------
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Select Skill",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )

                    ExposedDropdownMenuBox(
                        expanded = skillExpanded,
                        onExpandedChange = { skillExpanded = !skillExpanded },
                    ) {
                        TextField(
                            value = if (selectedSkill.isBlank()) "Choose a skill" else selectedSkill,
                            onValueChange = { /* read-only */ },
                            readOnly = true,
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color(0xFF222222),
                                focusedContainerColor = Color(0xFF222222),
                                unfocusedTextColor = Color.White,
                                focusedTextColor = Color.White,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                cursorColor = Color(0xFF7C3AED),
                                disabledContainerColor = Color(0xFF222222),
                            ),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = skillExpanded)
                            }
                        )

                        ExposedDropdownMenu(
                            expanded = skillExpanded,
                            onDismissRequest = { skillExpanded = false }
                        ) {
                            skills.forEach { skill ->
                                DropdownMenuItem(
                                    text = { Text(skill) },
                                    onClick = {
                                        selectedSkill = skill
                                        skillExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // -------- Duration ----------
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Duration",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "Hours",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color(0xFFAAAAAA)
                            )
                            TextField(
                                value = hoursText,
                                onValueChange = { value ->
                                    // allow only digits
                                    if (value.all { it.isDigit() } && value.length <= 3) {
                                        hoursText = value.ifEmpty { "0" }
                                    }
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = Color(0xFF222222),
                                    focusedContainerColor = Color(0xFF222222),
                                    unfocusedTextColor = Color.White,
                                    focusedTextColor = Color.White,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    cursorColor = Color(0xFF7C3AED),
                                )
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "Minutes",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color(0xFFAAAAAA)
                            )
                            TextField(
                                value = minutesText,
                                onValueChange = { value ->
                                    if (value.all { it.isDigit() } && value.length <= 2) {
                                        minutesText = value.ifEmpty { "0" }
                                    }
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = Color(0xFF222222),
                                    focusedContainerColor = Color(0xFF222222),
                                    unfocusedTextColor = Color.White,
                                    focusedTextColor = Color.White,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    cursorColor = Color(0xFF7C3AED),
                                )
                            )
                        }
                    }
                }

                // -------- Notes ----------
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Notes (Optional)",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    TextField(
                        value = notesText,
                        onValueChange = { notesText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp),
                        placeholder = {
                            Text(
                                text = "What did you work on?",
                                color = Color(0xFF666666)
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFF222222),
                            focusedContainerColor = Color(0xFF222222),
                            unfocusedTextColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            cursorColor = Color(0xFF7C3AED),
                        )
                    )
                }

                // -------- Save Button ----------
                Button(
                    onClick = {
                        if (isSaveEnabled) {
                            onSave(selectedSkill, totalMinutes, notesText.trim())
                        }
                    },
                    enabled = isSaveEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7C3AED),
                        disabledContainerColor = Color(0xFF3A2F66)
                    )
                ) {
                    Text(
                        text = "+ Save Session",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun QuickLogDialogPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000)),
        contentAlignment = Alignment.Center
    ) {
        QuickLogDialog(
            skills = listOf("Android", "Kotlin", "System Design"),
            initialSkill = "",
            onDismissRequest = {},
            onSave = { _, _, _ -> }
        )
    }
}
