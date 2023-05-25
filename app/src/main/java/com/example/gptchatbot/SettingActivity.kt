package com.example.gptchatbot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.example.gptchatbot.databinding.ActivityMainBinding
import com.example.gptchatbot.databinding.ActivitySettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEvents()
    }

    private fun setupEvents() {
        val temperatureSeekBar = binding.temperatureSeekBar
        val frequencyPenaltySeekBar = binding.frequencyPenaltySeekBar
        val temperatureNum = binding.temperatureNumber
        val frequencyPenaltyNum = binding.frequencyPenaltyNumber

        temperatureSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                val num = progress / 10.0
                temperatureNum.setText("" + num)
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
            }
        })


        frequencyPenaltySeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                val num = progress / 10.0
                frequencyPenaltyNum.setText("" + num)
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
            }
        })

        binding.chatResetBtn.setOnClickListener {
            finish()
        }
    }
}