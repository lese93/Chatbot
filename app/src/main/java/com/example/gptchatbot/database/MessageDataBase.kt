package com.example.gptchatbot.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gptchatbot.dao.MessageDAO
import com.example.gptchatbot.data.Message

@Database(entities = [Message::class], version = 1)
abstract class MessageDataBase: RoomDatabase() {
    abstract fun messageDAO(): MessageDAO
}