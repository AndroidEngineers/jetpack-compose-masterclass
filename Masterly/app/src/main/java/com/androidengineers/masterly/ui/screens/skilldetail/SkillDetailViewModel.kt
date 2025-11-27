package com.androidengineers.masterly.ui.screens.skilldetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidengineers.masterly.data.local.Skill
import com.androidengineers.masterly.domain.repository.SkillsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SkillDetailUiState {
    data object Loading : SkillDetailUiState
    data class Success(val skill: Skill) : SkillDetailUiState
    data class Error(val message: String) : SkillDetailUiState
}

@HiltViewModel
class SkillDetailViewModel @Inject constructor(
    private val skillsRepository: SkillsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val skillId: String = checkNotNull(savedStateHandle["skillId"])

    val uiState: StateFlow<SkillDetailUiState> = skillsRepository.getSkillById(skillId.toIntOrNull() ?: -1)
        .map { skill ->
            if (skill != null) {
                SkillDetailUiState.Success(skill)
            } else {
                SkillDetailUiState.Error("Skill not found")
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SkillDetailUiState.Loading
        )

    fun updateSkillName(newName: String) {
        val currentState = uiState.value
        if (currentState is SkillDetailUiState.Success) {
            viewModelScope.launch {
                skillsRepository.updateSkill(currentState.skill.copy(name = newName))
            }
        }
    }

    fun deleteSkill(onDeleteSuccess: () -> Unit) {
        val currentState = uiState.value
        if (currentState is SkillDetailUiState.Success) {
            viewModelScope.launch {
                skillsRepository.deleteSkill(currentState.skill)
                onDeleteSuccess()
            }
        }
    }
}
