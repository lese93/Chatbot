package com.example.gptchatbot.viewmodel


import android.content.SharedPreferences
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gptchatbot.api.Gpt3Api
import com.example.gptchatbot.data.Gpt3Request
import com.example.gptchatbot.data.Message
import com.example.gptchatbot.repository.MessageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val messageRepo: MessageRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    init {
        viewModelScope.launch {
            messageRepo.init()
        }
    }

    val liveData = messageRepo.liveData

    fun sendMessage(message: Message) {
        viewModelScope.launch {
            messageRepo.addMessageData(message)
            var gpt3Message: Message
            try {
                val gpt3Api = Gpt3Api.create()
                val request = Gpt3Request(
                    prompt = message.content,
                    temperature = sharedPreferences.getFloat("temperature", 0f),
                    frequency_penalty = sharedPreferences.getFloat("frequency_penalty", 0f)
                )
                // response
                val resp = gpt3Api.sendMessage(request)
                val choice = resp.choices[0]
                gpt3Message = Message(
                    sender = 1,
                    content = choice.text,
                    timestamp = System.currentTimeMillis()
                )

                messageRepo.addMessageData(gpt3Message)
                Log.d("GptResp", "" + resp.toString())
            } catch (e: HttpException) {
                gpt3Message = Message(
                    sender = 3,
                    content = "Too Many Requests",
                    timestamp = System.currentTimeMillis()
                )
                messageRepo.addMessageData(gpt3Message)
                Log.e("GptResp", "HttpException")
            } catch (e: Exception) {
                gpt3Message = Message(
                    sender = 3,
                    content = "" + e.message,
                    timestamp = System.currentTimeMillis()
                )
                messageRepo.addMessageData(gpt3Message)
                Log.e("GptResp", "Exception: " + e.message)
            }
        }
    }

    fun loadMessages(): List<Message> {
        return messageRepo.get()
    }


}
