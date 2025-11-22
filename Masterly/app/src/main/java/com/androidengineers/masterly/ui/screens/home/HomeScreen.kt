package com.androidengineers.masterly.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.androidengineers.masterly.ui.components.SkillCard
import kotlin.collections.get

@Preview()
@Composable
fun HomeScreenPreview() {

}

@Composable
fun HomeScreen(
    modifier: Modifier =  Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navigateToTimer: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is DashboardUiState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is DashboardUiState.Empty -> {
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("No skills yet")
                    Button(onClick = {

                    }) {
                        Text("Add Skill")
                    }
                }
            }
        }

        is DashboardUiState.Error -> {
            Box(
                modifier = modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(state.message)
                    Button(onClick = {

                    }) {
                        Text("Retry")
                    }
                }
            }
        }

        is DashboardUiState.Content -> {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(count = state.skills.size, key = {
                    state.skills[it].id
                }) { index ->
                    val skill = state.skills[index]
                    SkillCard(
                        skill.name,
                        skill.minutesPracticed,
                        skill.goalMinutes,
                        onClick = {
                            navigateToTimer(skill.name)
                        }
                    )
                }
            }
        }
    }
}
