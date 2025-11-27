package com.androidengineers.masterly.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun QuickLogDialog(
    onDismissRequest: () -> Unit,
    onAddNewSkill: (name: String) -> Unit,
) {
    AddSkillDialog(
        onSave = { name ->
            onAddNewSkill(name)
        },
        onDismissRequest = onDismissRequest
    )
}

@Composable
private fun AddSkillDialog(
    onDismissRequest: () -> Unit,
    onSave: (name: String) -> Unit
) {
    var name by remember { mutableStateOf("") }

    val isSaveEnabled = name.isNotBlank()

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF111111)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Add New Skill",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )

                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Skill Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFF222222),
                        focusedContainerColor = Color(0xFF222222),
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White,
                        cursorColor = Color(0xFF7C3AED)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onDismissRequest,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            onSave(name)
                            onDismissRequest()
                        },
                        enabled = isSaveEnabled,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED))
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun QuickLogDialogPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        QuickLogDialog(
            onDismissRequest = {},
            onAddNewSkill = { _ -> }
        )
    }
}

@Preview
@Composable
private fun AddSkillDialogPreview() {
    AddSkillDialog(onDismissRequest = {}, onSave = { _ -> })
}