package com.example.gptchatbot.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.gptchatbot.dao.MessageDAO
import com.example.gptchatbot.data.Message
import com.example.gptchatbot.database.MessageDataBase
import com.example.gptchatbot.repository.MessageRepository
import com.example.gptchatbot.util.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMessageDatabase(@ApplicationContext context: Context): MessageDataBase {
        return Room
            .databaseBuilder(
                context,
                MessageDataBase::class.java,
                "message.db"
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideMessageDAO(messageDB: MessageDataBase): MessageDAO {
        return messageDB.messageDAO()
    }

    @Singleton
    @Provides
    fun provideMessageRepository(messageDAO: MessageDAO,
                                 sharedPreferencesHelper: SharedPreferencesHelper): MessageRepository {
        return MessageRepository(messageDAO,sharedPreferencesHelper)
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("chat_bot_preferences", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSharedPreferencesHelper(sharedPreferences: SharedPreferences): SharedPreferencesHelper {
        return SharedPreferencesHelper(sharedPreferences)
    }


}