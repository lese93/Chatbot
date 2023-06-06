package com.example.gptchatbot.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.gptchatbot.data.Message
import com.example.gptchatbot.databinding.OpponentListItemBinding

class OpponentViewHolder(private val binding: OpponentListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(message: Message, positon: Int, selectedMessages: MutableList<Message>) {
        binding.opponentMessage.text = message.content

        with(binding.checkbox) {
            isChecked = selectedMessages.contains(message)
        }
        binding.checkbox.setOnClickListener {

            if (binding.checkbox.isChecked) {
                selectedMessages.add(message)
            } else {
                selectedMessages.remove(message)
            }
        }
    }

    fun setCheckBoxVisibility(showCheckBox: Boolean) {
        binding.checkbox.visibility = if (showCheckBox) View.VISIBLE else View.GONE
    }
}