package com.example.gptchatbot.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gptchatbot.dao.SettingDAO
import com.example.gptchatbot.data.Setting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SettingRepository constructor(private val settingDAO: SettingDAO) {
    val liveData: MutableLiveData<List<Setting>> = MutableLiveData(mutableListOf())

    suspend fun addSettingData(data: Setting) {
        withContext(Dispatchers.IO) {
            settingDAO.insertSetting(data)
        }
        Log.e("insertData", "" + data)
    }

    suspend fun init() {
        val settingData = withContext(Dispatchers.IO) {
            settingDAO.getSetting()
        }
        Log.e("getSetting", "" + settingData)
        liveData.value = settingData
    }

    fun get(): List<Setting> {
        return liveData.value ?: mutableListOf()
    }
}
