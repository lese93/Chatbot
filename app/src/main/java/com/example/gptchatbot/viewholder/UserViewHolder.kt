package com.example.gptchatbot.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gptchatbot.R

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val message: TextView = itemView.findViewById(R.id.user_message)
}