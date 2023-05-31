package com.example.gptchatbot.viewmodel

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    companion object {
        private const val TEMPERATURE_KEY = "temperature"
        private const val FREQUENCY_PENALTY_KEY = "frequency_penalty"
    }

    fun saveTemperature(temperature: Float) {
        sharedPreferences.edit {
            putFloat(TEMPERATURE_KEY, temperature)
        }
    }

    fun saveFrequencyPenalty(frequencyPenalty: Float) {
        sharedPreferences.edit {
            putFloat(FREQUENCY_PENALTY_KEY, frequencyPenalty)
        }
    }

    fun getTemperature(): Float {
        return sharedPreferences.getFloat(TEMPERATURE_KEY, 0f)
    }

    fun getFrequencyPenalty(): Float {
        return sharedPreferences.getFloat(FREQUENCY_PENALTY_KEY, 0f)
    }
}