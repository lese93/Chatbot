package com.example.gptchatbot.data

data class Gpt3Choice(
    val text: String,
    val index: Int,
    val finish_reason: String
)