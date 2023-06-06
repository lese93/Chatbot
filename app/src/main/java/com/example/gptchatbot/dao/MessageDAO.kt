package com.example.gptchatbot.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gptchatbot.data.Message

@Dao
interface MessageDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: Message)

    @Query("SELECT * FROM message ORDER BY id ASC")
    fun getAllMessages(): List<Message>
    @Query("DELETE FROM message")
    fun deleteAllMessages()
}