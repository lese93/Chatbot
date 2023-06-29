package com.example.gptchatbot.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.gptchatbot.data.Message
import com.example.gptchatbot.databinding.NoticeListItemBinding

class NoticeViewHolder(private val binding: NoticeListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(message: Message, positon: Int, selectedMessages: MutableList<Message>) {
        binding.noticeMessage.text = message.content

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