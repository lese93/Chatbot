package com.example.gptchatbot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.activity.viewModels
import com.example.gptchatbot.databinding.ActivitySettingBinding
import com.example.gptchatbot.viewmodel.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {
    private val viewModel by viewModels<SettingViewModel>()
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
        val chatResetBtn = binding.chatResetBtn
        val shareBtn = binding.shareBtn


        temperatureNum.text = viewModel.getTemperature().toString()
        frequencyPenaltyNum.text = viewModel.getFrequencyPenalty().toString()

        with(temperatureSeekBar) {

            // seekbar 핸들 조정 
            // 값이 정수형으로만 세팅 가능해서 정수 <-> 소수 변환 필요
            progress = (viewModel.getTemperature() * 10).toInt()

            setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seek: SeekBar,
                    progress: Int, fromUser: Boolean
                ) {
                    val num = progress / 10.0
                    temperatureNum.text = "$num"
                }

                override fun onStartTrackingTouch(seek: SeekBar) {
                }

                override fun onStopTrackingTouch(seek: SeekBar) {
                }
            })
        }


        with(frequencyPenaltySeekBar) {

            progress = (viewModel.getFrequencyPenalty() * 10).toInt()

            setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seek: SeekBar,
                    progress: Int, fromUser: Boolean
                ) {
                    val num = progress / 10.0
                    frequencyPenaltyNum.text = "$num"
                }

                override fun onStartTrackingTouch(seek: SeekBar) {
                }

                override fun onStopTrackingTouch(seek: SeekBar) {
                }
            })
        }

        chatResetBtn.setOnClickListener {
            viewModel.deleteAllMessages()

            finish()
        }

        shareBtn.setOnClickListener {
            val returnIntent = Intent()
            returnIntent.putExtra("shareMode", true)
            setResult(RESULT_OK, returnIntent)
            finish()
        }

    }

    override fun onDestroy() {
        val temperatureValue = binding.temperatureNumber.text.toString().toFloat()
        val frequencyPenaltyValue = binding.frequencyPenaltyNumber.text.toString().toFloat()

        viewModel.saveTemperature(temperatureValue)
        viewModel.saveFrequencyPenalty(frequencyPenaltyValue)
        super.onDestroy()
    }
}