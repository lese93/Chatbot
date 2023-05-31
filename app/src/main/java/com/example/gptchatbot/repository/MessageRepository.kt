package com.example.gptchatbot.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gptchatbot.dao.MessageDAO
import com.example.gptchatbot.data.Message
import com.example.gptchatbot.notifyObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MessageRepository constructor(
    private val messageDAO: MessageDAO
) {
    val liveData: MutableLiveData<MutableList<Message>> = MutableLiveData(mutableListOf())

    suspend fun addMessageData(data: Message) {
        withContext(Dispatchers.IO) {
            messageDAO.insertMessage(data)
        }

        liveData.value?.add(data)
        liveData.notifyObserver()
    }

    suspend fun init() {
        val messageData = withContext(Dispatchers.IO) {
            messageDAO.getAllMessages()
        }

        liveData.value = messageData.toMutableList()
    }

    fun get(): List<Message> {
        return liveData.value ?: mutableListOf()
    }
}