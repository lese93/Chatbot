package com.example.gptchatbot.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gptchatbot.R
import com.example.gptchatbot.data.Message
import com.example.gptchatbot.databinding.NoticeListItemBinding
import com.example.gptchatbot.databinding.OpponentListItemBinding
import com.example.gptchatbot.databinding.UserListItemBinding
import com.example.gptchatbot.viewholder.NoticeViewHolder
import com.example.gptchatbot.viewholder.OpponentViewHolder
import com.example.gptchatbot.viewholder.UserViewHolder

class MessageAdapter(private val context: Context, private var messageList: List<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val selectedMessages = mutableListOf<Message>()
    private var showCheckBox = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            0 -> {
                val binding = UserListItemBinding.inflate(layoutInflater, parent, false)
                UserViewHolder(binding)
            }
            1 -> {
                val binding = OpponentListItemBinding.inflate(layoutInflater, parent, false)
                OpponentViewHolder(binding)
            }
            else -> {
                val binding = NoticeListItemBinding.inflate(layoutInflater, parent, false)
                NoticeViewHolder(binding)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        when (holder) {
            is UserViewHolder -> {
                holder.bind(currentMessage, position, selectedMessages)
                holder.setCheckBoxVisibility(showCheckBox)
            }
            is OpponentViewHolder -> {
                holder.bind(currentMessage, position, selectedMessages)
                holder.setCheckBoxVisibility(showCheckBox)
            }
            is NoticeViewHolder -> {
                holder.bind(currentMessage, position, selectedMessages)
                holder.setCheckBoxVisibility(showCheckBox)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = messageList[position]
        return message.sender
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    fun showCheckBox(show: Boolean) {
        showCheckBox = show
        notifyDataSetChanged()
    }

    fun getSelectedMessages(): List<Message> {
        return selectedMessages
    }

    fun submitList(messages: List<Message>) {
        this.messageList = messages
        notifyDataSetChanged()
    }
}