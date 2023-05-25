package com.example.gptchatbot.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Setting(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "temperature") val temperature: Float,
    @ColumnInfo(name = "frequency_panalty") val frequencyPanalty: Float,
)