package com.example.gptchatbot.data

data class Gpt3Request(
    val model: String = "text-davinci-003",
    val prompt: String = "",
    val max_tokens: Int = 3900,
    val temperature: Float = 0F,
    val frequency_penalty: Float = 0F,
    val presence_penalty: Int = 0,
    val top_p: Int = 1
)
