package com.example.gptchatbot

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gptchatbot.adapter.MessageAdapter
import com.example.gptchatbot.data.Message
import com.example.gptchatbot.databinding.ActivityMainBinding
import com.example.gptchatbot.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEvents()
    }

    private fun setupEvents() {

        viewModel.liveData.observe(this) {
            messageAdapter = MessageAdapter(this, viewModel.loadMessages())
            with(binding.chatRecyclerView) {
                layoutManager = GridLayoutManager(context, 1)
                adapter = messageAdapter
            }

//            messageAdapter.notifyDataSetChanged()
            Log.e("adapter Item Count", "" + messageAdapter.itemCount)
            binding.chatRecyclerView.scrollToPosition(messageAdapter.itemCount - 1)
        }

        binding.messageEditText.addTextChangedListener { editable ->
            val content = editable?.toString() ?: ""
            binding.sendBtn.isEnabled = content.isNotEmpty()
        }

        binding.settingBtn.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        binding.sendBtn.setOnClickListener {
            val sender = 0 // 0 = User, 1 = GPT
            val content = binding.messageEditText.text.toString()
            val timestamp = System.currentTimeMillis()

            val message = Message(sender = sender, content = content, timestamp = timestamp)

            hideKeyboard()
            binding.messageEditText.setText("")
            viewModel.sendMessage(message)
        }
    }


    private fun hideKeyboard() {
        val currentFocus = currentFocus
        if (currentFocus != null) {
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                currentFocus.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}