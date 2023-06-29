package com.example.gptchatbot.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gptchatbot.data.Message
import com.example.gptchatbot.repository.MessageRepository
import com.example.gptchatbot.util.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val messageRepo: MessageRepository
) : ViewModel() {
    init {
        viewModelScope.launch {
            messageRepo.init()
        }
    }

    val liveData = messageRepo.liveData
    val toastLiveData: MutableLiveData<String> = MutableLiveData()


    fun sendMessage(message: Message) {
        viewModelScope.launch {
            try {
                messageRepo.sendMessage(message)
            } catch (e: Exception) {
                toastLiveData.postValue(e.message.toString())
            }
        }
    }

    fun resetToast() {
        toastLiveData.postValue("")
    }


    fun loadMessages(): List<Message> {
        return messageRepo.get()
    }

}