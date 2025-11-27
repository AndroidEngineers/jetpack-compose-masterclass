package com.androidengineers.masterly.ui.screens.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import androidx.lifecycle.SavedStateHandle
import com.androidengineers.masterly.domain.repository.SkillsRepository
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val skillsRepository: SkillsRepository
) : ViewModel() {

    private val skillId: String = checkNotNull(savedStateHandle["skillId"])

    private val _time = MutableStateFlow("00:00:00")
    val time: StateFlow<String> = _time

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    private val _skillName = MutableStateFlow("")
    val skillName: StateFlow<String> = _skillName

    private var startTimeMillis = 0L
    private var accumulatedTimeMillis = 0L

    private var timerJob: Job? = null

    init {
        fetchSkill()
    }

    private fun fetchSkill() {
        viewModelScope.launch {
            skillsRepository.getSkillById(skillId.toIntOrNull() ?: -1).collect { skill ->
                skill?.let {
                    _skillName.value = it.name
                }
            }
        }
    }

    fun toggleTimer() {
        if (_isRunning.value) {
            pauseTimer()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        _isRunning.value = true
        startTimeMillis = System.currentTimeMillis()
        
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_isRunning.value) {
                val currentRunTime = System.currentTimeMillis() - startTimeMillis
                updateFormattedTime(accumulatedTimeMillis + currentRunTime)
                delay(50) // Update more frequently for smoothness
            }
        }
    }

    private fun pauseTimer() {
        if (_isRunning.value) {
            _isRunning.value = false
            timerJob?.cancel()
            accumulatedTimeMillis += System.currentTimeMillis() - startTimeMillis
            updateFormattedTime(accumulatedTimeMillis)
        }
    }

    fun resetTimer() {
        _isRunning.value = false
        timerJob?.cancel()
        accumulatedTimeMillis = 0L
        updateFormattedTime(0L)
    }



    fun saveSession(onSaveSuccess: () -> Unit) {
        pauseTimer()
        println("DEBUG: saveSession - accumulated: $accumulatedTimeMillis, skillId: $skillId")
        
        if (accumulatedTimeMillis > 0) {
            viewModelScope.launch {
                val id = skillId.toIntOrNull()
                if (id == null) {
                    println("DEBUG: Invalid skillId: $skillId")
                    return@launch
                }
                
                val skill = skillsRepository.getSkillById(id).first()
                println("DEBUG: Fetched skill: $skill")
                
                if (skill != null) {
                    val updatedSkill = skill.copy(
                        millisPracticed = skill.millisPracticed + accumulatedTimeMillis
                    )
                    println("DEBUG: Updating skill to: $updatedSkill")
                    skillsRepository.updateSkill(updatedSkill)
                    onSaveSuccess()
                } else {
                    println("DEBUG: Skill is null")
                    onSaveSuccess()
                }
            }
        } else {
            println("DEBUG: No time accumulated")
            onSaveSuccess() // Nothing to save
        }
    }

    private fun updateFormattedTime(millis: Long) {
        val totalSec = millis / 1000
        val h = totalSec / 3600
        val m = (totalSec % 3600) / 60
        val s = totalSec % 60
        _time.value = String.format("%02d:%02d:%02d", h, m, s)
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
