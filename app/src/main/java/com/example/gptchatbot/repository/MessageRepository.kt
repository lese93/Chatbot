package com.example.gptchatbot.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gptchatbot.dao.MessageDAO
import com.example.gptchatbot.dao.SettingDAO
import com.example.gptchatbot.data.Message
import com.example.gptchatbot.data.Setting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessageRepository constructor(private val messageDAO: MessageDAO) {
    val liveData: MutableLiveData<List<Message>> = MutableLiveData(mutableListOf())

    suspend fun addMessageData(data: Message) {
        withContext(Dispatchers.IO) {
            messageDAO.insertMessage(data)
        }
        Log.e("insertData", "" + data)
    }

    suspend fun init() {
        val messageData = withContext(Dispatchers.IO) {
            messageDAO.getAllMessage()
        }
        Log.e("getSetting", "" + messageData)
        liveData.value = messageData
    }

    fun get(): List<Message> {
        return liveData.value ?: mutableListOf()
    }
}