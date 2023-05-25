package com.example.gptchatbot.di

import com.example.gptchatbot.dao.MessageDAO
import com.example.gptchatbot.database.MessageDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMessageDAO(messageDB: MessageDataBase): MessageDAO {
        return messageDB.messageDAO()
    }
}