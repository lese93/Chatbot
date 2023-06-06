package com.example.gptchatbot.viewmodel


import androidx.lifecycle.ViewModel
import com.example.gptchatbot.repository.MessageRepository
import com.example.gptchatbot.util.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val messageRepo: MessageRepository,
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : ViewModel() {

    fun saveTemperature(temperature: Float) {
        sharedPreferencesHelper.saveTemperature(temperature)
    }

    fun saveFrequencyPenalty(frequencyPenalty: Float) {
        sharedPreferencesHelper.saveFrequencyPenalty(frequencyPenalty)
    }

    fun getTemperature(): Float {
        return sharedPreferencesHelper.getTemperature()
    }

    fun getFrequencyPenalty(): Float {
        return sharedPreferencesHelper.getFrequencyPenalty()
    }

    fun deleteAllMessages() {
        messageRepo.deleteAllMessages()
    }

}