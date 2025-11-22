package com.androidengineers.masterly.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.androidengineers.masterly.ui.theme.MasterlyTheme
import kotlinx.coroutines.delay

@Composable
fun RememberUpdatedStateDemo(modifier: Modifier = Modifier) {
    var message by remember { mutableStateOf("Initial message") }
    // A counter to trigger the effects when the button is clicked
    var trigger by remember { mutableStateOf(0) }

    var wrongResult by remember { mutableStateOf("Click the button to start") }
    var rightResult by remember { mutableStateOf("Click the button to start") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "remember vs. rememberUpdatedState",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Message") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White,
                focusedLabelColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                focusedContainerColor = Color(0xFF333333),
                unfocusedContainerColor = Color(0xFF333333),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                // Clear previous results and trigger the effects
                wrongResult = "Timer started..."
                rightResult = "Timer started..."
                trigger++
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Show message after 3 seconds")
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Instructions: Click the button, then change the message within 3 seconds to see the difference.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.LightGray
        )

        Spacer(Modifier.height(16.dp))

        // Example 1: `remember`
        Text(
            text = "❌ 1. Using `remember` (Incorrect):",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
        Text(
            text = "The LaunchedEffect captures the `message` at the moment the timer starts. It won't see any subsequent changes.",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Text(text = "Result: $wrongResult", color = Color.White)

        Spacer(Modifier.height(16.dp))

        // Example 2: `rememberUpdatedState`
        Text(
            text = "✅ 2. Using `rememberUpdatedState` (Correct):",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
        Text(
            text = "`rememberUpdatedState` provides the latest `message` value to the effect, even though the effect itself doesn't restart.",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Text(text = "Result: $rightResult", color = Color.White)

        // The child composables that run the delayed tasks
        IncorrectDelayedReader(
            trigger = trigger,
            message = message,
            onTimeout = { value ->
                wrongResult = value
            }
        )

        CorrectDelayedReader(
            trigger = trigger,
            message = message,
            onTimeout = { value ->
                rightResult = value
            }
        )
    }
}

/**
 * ❌ INCORRECT: The LaunchedEffect captures the `message` on launch.
 */
@Composable
private fun IncorrectDelayedReader(
    trigger: Int,
    message: String,
    onTimeout: (String) -> Unit
) {
    // This effect re-launches whenever `trigger` changes.
    LaunchedEffect(trigger) {
        // We don't want this to run on the initial composition
        if (trigger == 0) return@LaunchedEffect

        delay(3000)
        // It uses the `message` value captured 3 seconds ago when the effect was launched.
        onTimeout("The message was: '$message'")
    }
}

/**
 * ✅ CORRECT: Uses `rememberUpdatedState` to access the latest `message`.
 */
@Composable
private fun CorrectDelayedReader(
    trigger: Int,
    message: String,
    onTimeout: (String) -> Unit
) {
    // `latestMessage` is a reference that is always updated on every recomposition.
    val latestMessage by rememberUpdatedState(message)

    LaunchedEffect(trigger) {
        if (trigger == 0) return@LaunchedEffect

        delay(3000)
        // Reading `latestMessage` here gives us the most current value,
        // even though the effect was launched 3 seconds ago.
        onTimeout("The message is: '$latestMessage'")
    }
}

@Preview(showBackground = true)
@Composable
private fun RememberUpdatedStateDemoPreview() {
    MasterlyTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF121212)) {
            RememberUpdatedStateDemo()
        }
    }
}
