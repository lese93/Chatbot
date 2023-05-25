package com.example.gptchatbot.database

import androidx.room.Database

import com.example.gptchatbot.dao.SettingDAO
import com.example.gptchatbot.data.Setting

@Database(entities = [Setting::class], version = 1)
abstract class SettingDataBase {
    abstract fun settingDAO(): SettingDAO
}