package com.example.gptchatbot.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gptchatbot.R
import com.example.gptchatbot.data.Message
import com.example.gptchatbot.viewholder.NoticeViewHolder
import com.example.gptchatbot.viewholder.OpponentViewHolder
import com.example.gptchatbot.viewholder.UserViewHolder

class MessageAdapter(private val context: Context, private val messageList: List<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            0 -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false)
                UserViewHolder(view)
            }
            1 -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.opponent_list_item, parent, false)
                OpponentViewHolder(view)

            }
            else -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.notice_list_item, parent, false)
                NoticeViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        when (holder) {
            is UserViewHolder -> {
                holder.message.text = currentMessage.content
            }
            is OpponentViewHolder -> {
                holder.message.text = currentMessage.content
            }
            is NoticeViewHolder -> {
                holder.message.text = currentMessage.content
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


}