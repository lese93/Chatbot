package com.example.gptchatbot.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gptchatbot.R
import com.example.gptchatbot.data.Message
import com.example.gptchatbot.viewholder.OpponentViewHolder
import com.example.gptchatbot.viewholder.UserViewHolder

class MessageAdapter(private val context: Context, private val messageList: MutableList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false)
            OpponentViewHolder(view)
        } else {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.opponent_list_item, parent, false)
            UserViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if (holder.javaClass == OpponentViewHolder::class.java) {
            val viewHolder = holder as OpponentViewHolder
            viewHolder.message.text = currentMessage.content
        } else {
            val viewHolder = holder as UserViewHolder
            viewHolder.message.text = currentMessage.content
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