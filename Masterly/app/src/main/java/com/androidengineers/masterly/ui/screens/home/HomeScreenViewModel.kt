package com.androidengineers.masterly.ui.screens.home

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Stable
data class Skill(
    val id: String,
    val name: String,
    val minutesPracticed: Int,
    val goalMinutes: Int
)

sealed class DashboardUiState {
    data object Loading : DashboardUiState()
    data class Content(val skills: List<Skill>) : DashboardUiState()
    data object Empty : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(): ViewModel() {

    private val _dummySkills = MutableStateFlow(
        listOf(
            Skill("1", "Android Development", 1450, 10000 * 60),
            Skill("2", "Kotlin", 620, 5000 * 60),
            Skill("3", "Jetpack Compose", 980, 3000 * 60),
            Skill("4", "UI/UX Design", 260, 2000 * 60),
            Skill("5", "System Design", 140, 1500 * 60),
            Skill("6", "DSA", 450, 2000 * 60),
            Skill("7", "Public Speaking", 90, 500 * 60)
        )
    )

    private val _uiState: MutableStateFlow<DashboardUiState> =
        MutableStateFlow(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        observeSkills()
    }

    private fun observeSkills() {
        viewModelScope.launch {
            _dummySkills
                .map { skills ->
                    if (skills.isEmpty())
                        DashboardUiState.Empty
                    else
                        DashboardUiState.Content(skills.sortedBy { it.name.lowercase() })
                }
                .onStart { emit(DashboardUiState.Loading) }
                .catch { throwable ->
                    emit(DashboardUiState.Error(throwable.message ?: "Failed to load skills"))
                }
                .collect { newState -> _uiState.value = newState }
        }
    }

    fun addSkill(name: String, goalMinutes: Int) {
        viewModelScope.launch {
            runCatching {
                val newSkill = Skill(
                    id = System.currentTimeMillis().toString(),
                    name = name,
                    minutesPracticed = 0,
                    goalMinutes = goalMinutes
                )
                _dummySkills.update { oldList ->
                    oldList + newSkill
                }
            }.onFailure { throwable ->
                _uiState.update { DashboardUiState.Error(throwable.message ?: "Unknown error") }
            }
        }
    }

}