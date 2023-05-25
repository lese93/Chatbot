package com.example.gptchatbot.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gptchatbot.data.Setting

@Dao
interface SettingDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSetting(setting: Setting)

    @Query("SELECT * FROM setting ORDER BY id ASC")
    fun getSetting(): List<Setting>
}

