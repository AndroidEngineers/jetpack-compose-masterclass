package com.androidengineers.masterly.ui.screens.home

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidengineers.masterly.data.local.Skill
import com.androidengineers.masterly.domain.usecase.AddSkillUseCase
import com.androidengineers.masterly.domain.usecase.GetSkillsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DashboardUiState {
    data object Loading : DashboardUiState()
    data class Content(val skills: List<Skill>) : DashboardUiState()
    data object Empty : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}

sealed interface DashboardEvent {
    data class AddSkill(
        val name: String,
        val goalMinutes: Int
    ) : DashboardEvent
}

sealed interface DashboardEffect {
    data class ShowError(val message: String) : DashboardEffect
    data object SkillAdded : DashboardEffect
}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getSkillsUseCase: GetSkillsUseCase,
    private val addSkillUseCase: AddSkillUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<DashboardUiState> = MutableStateFlow(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private val _effects = MutableSharedFlow<DashboardEffect>()
    val effects = _effects

    init {
        observeSkills()
    }

    private fun observeSkills() {
        viewModelScope.launch {
            getSkillsUseCase()
                .map { skills ->
                    if (skills.isEmpty())
                        DashboardUiState.Empty
                    else
                        DashboardUiState.Content(skills.sortedBy { it.name.lowercase() })
                }
                .onStart { emit(DashboardUiState.Loading) }
                .catch { throwable ->
                    emit(DashboardUiState.Error(throwable.message ?: "Failed to load skills"))
                    _effects.emit(
                        DashboardEffect.ShowError(
                            throwable.message ?: "Failed to load skills"
                        )
                    )
                }
                .collect { newState -> _uiState.value = newState }
        }
    }

    fun onEvent(event: DashboardEvent) {
        when (event) {
            is DashboardEvent.AddSkill -> handleAddSkill(
                name = event.name,
                goalMinutes = event.goalMinutes
            )
        }
    }

    private fun handleAddSkill(name: String, goalMinutes: Int) {
        viewModelScope.launch {
            runCatching {
                val newSkill = Skill(
                    name = name,
                    minutesPracticed = 0,
                    goalMinutes = goalMinutes
                )
                addSkillUseCase(newSkill)
            }.onSuccess {
                _effects.emit(DashboardEffect.SkillAdded)
            }.onFailure { throwable ->
                val msg = throwable.message ?: "Unknown error"
                _uiState.update { DashboardUiState.Error(throwable.message ?: msg) }
                _effects.emit(DashboardEffect.ShowError(msg))
            }
        }
    }
}