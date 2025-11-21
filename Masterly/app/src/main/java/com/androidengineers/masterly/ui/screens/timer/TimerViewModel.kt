package com.androidengineers.masterly.ui.screens.timer

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor() : ViewModel() {

    private val _time = MutableStateFlow("00:01:00")
    val time: StateFlow<String> = _time

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    private val _stopButtonEnabled = MutableStateFlow(false)
    val stopButtonEnabled: StateFlow<Boolean> = _stopButtonEnabled

    private var totalTimeMillis = 60_000L   // 1 minute
    private var timeLeftMillis = totalTimeMillis

    private var timerJob: Job? = null

    fun toggleTimer() {
        if (_isRunning.value) {
            stopTimerInternal()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        _isRunning.value = true
        _stopButtonEnabled.value = true

        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (timeLeftMillis > 0) {
                delay(1000)
                timeLeftMillis -= 1000
                updateFormattedTime()
            }
            finishTimer()
        }
    }

    private fun stopTimerInternal() {
        timerJob?.cancel()
        _isRunning.value = false
    }

    fun resetTimer() {
        timerJob?.cancel()
        timeLeftMillis = totalTimeMillis
        _isRunning.value = false
        _stopButtonEnabled.value = false
        updateFormattedTime()
    }

    fun stopTimer() {
        if (_stopButtonEnabled.value) {
            timerJob?.cancel()
            timeLeftMillis = 0
            updateFormattedTime()
            _isRunning.value = false
            _stopButtonEnabled.value = false
        }
    }

    private fun finishTimer() {
        _isRunning.value = false
        _stopButtonEnabled.value = false
        timeLeftMillis = 0
        updateFormattedTime()
    }

    private fun updateFormattedTime() {
        val totalSec = timeLeftMillis / 1000
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
