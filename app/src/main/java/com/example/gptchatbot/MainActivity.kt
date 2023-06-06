package com.example.gptchatbot

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gptchatbot.adapter.MessageAdapter
import com.example.gptchatbot.data.Message
import com.example.gptchatbot.databinding.ActivityMainBinding
import com.example.gptchatbot.viewmodel.MainViewModel
import com.google.android.material.internal.ViewUtils.hideKeyboard
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

        val settingActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data = result.data
                    val shareMode = data?.getBooleanExtra("shareMode", false) ?: false

                    if (shareMode) {
                        messageAdapter.showCheckBox(true)
                        binding.shareBtn.visibility = View.VISIBLE
                    }
                }
            }

        viewModel.liveData.observe(this) {
            messageAdapter = MessageAdapter(this, viewModel.loadMessages())

            with(binding.chatRecyclerView) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
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


        binding.shareBtn.setOnClickListener {
            val selectedMessages = messageAdapter.getSelectedMessages()

            // 선택된 메시지들을 다른 앱과 공유
            if (selectedMessages.isNotEmpty()) {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"

                // 선택된 메시지들을 문자열로 결합
                val sharedText = selectedMessages.joinToString(separator = "\n") { message ->
                    val sender =
                        when (message.sender) {
                            0-> "Me"
                            1-> "GPT"
                            else -> it
                        }
                    " $sender : ${message.content}"
                }
                Log.d("sharedText", "" + sharedText)

                shareIntent.putExtra(Intent.EXTRA_TEXT, sharedText)
                startActivity(shareIntent)
            }
        }

        binding.settingBtn.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            settingActivityResultLauncher.launch(intent)
        }


        binding.sendBtn.setOnClickListener {
            val sender = 0 // 0 = User, 1 = GPT
            val content = binding.messageEditText.text.toString()
            val timestamp = System.currentTimeMillis()

            val message = Message(sender = sender, content = content, timestamp = timestamp)
            val exceptionMessage:String?
            hideKeyboard()
            binding.messageEditText.setText("")
            viewModel.sendMessage(message)
        }
    }


    private fun hideKeyboard() {
        val currentFocus = currentFocus
        if (currentFocus != null) {
            val inputManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                currentFocus.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}


