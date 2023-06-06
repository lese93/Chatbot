package com.example.gptchatbot.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.gptchatbot.api.Gpt3Api
import com.example.gptchatbot.dao.MessageDAO
import com.example.gptchatbot.data.Gpt3Request
import com.example.gptchatbot.data.Message
import com.example.gptchatbot.notifyObserver
import com.example.gptchatbot.util.SharedPreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MessageRepository constructor(
    private val messageDAO: MessageDAO,
    private val sharedPreferencesHelper: SharedPreferencesHelper

) {
    val liveData: MutableLiveData<MutableList<Message>> = MutableLiveData(mutableListOf())

    suspend fun init() {
        val messageData = withContext(Dispatchers.IO) {
            messageDAO.getAllMessages()
        }

        liveData.postValue(messageData.toMutableList())
        liveData.notifyObserver()
    }

    fun get(): List<Message> {
        return liveData.value ?: mutableListOf()
    }

    private suspend fun addMessageData(data: Message) {
        withContext(Dispatchers.IO) {
            messageDAO.insertMessage(data)
            Log.d("sendMessage",""+ data.content)
        }

        liveData.value?.add(data)
        liveData.notifyObserver()
    }

    suspend fun sendMessage(message: Message): String? {
        addMessageData(message)
        var gpt3Message: Message
        var exceptionMessage: String? =""
        try {
            val gpt3Api = Gpt3Api.create()
            val request = Gpt3Request(
                prompt = message.content,
                temperature = sharedPreferencesHelper.getTemperature(),
                frequency_penalty = sharedPreferencesHelper.getFrequencyPenalty()
            )
            // response
            val resp = gpt3Api.sendMessage(request)
            val choice = resp.choices[0]
            gpt3Message = Message(
                sender = 1,
                content = choice.text,
                timestamp = System.currentTimeMillis()
            )

            addMessageData(gpt3Message)
            Log.d("GptResp", "" + resp.toString())
        } catch (e: HttpException) {
            exceptionMessage = e.message.toString()
            Log.e("GptResp", "HttpException: " + e.message)
        } catch (e: Exception) {
            exceptionMessage = e.message.toString()
            Log.e("GptResp", "Exception: " + e.message)
        }

        return exceptionMessage
    }

    fun deleteAllMessages() {
        GlobalScope.launch(Dispatchers.IO) {
            messageDAO.deleteAllMessages()
        }

        liveData.value?.clear()
        liveData.notifyObserver()
    }


}