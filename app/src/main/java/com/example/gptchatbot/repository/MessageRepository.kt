package com.example.gptchatbot.repository

import androidx.lifecycle.MutableLiveData
import com.example.gptchatbot.dao.MessageDAO
import com.example.gptchatbot.dao.SettingDAO
import com.example.gptchatbot.data.Message
import com.example.gptchatbot.data.Setting

class MessageRepository constructor(private val messageDAO: MessageDAO) {
    val liveData: MutableLiveData<List<Message>> = MutableLiveData(mutableListOf())


}