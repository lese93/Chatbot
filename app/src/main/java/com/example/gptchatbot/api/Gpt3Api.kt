package com.example.gptchatbot.api

import com.example.gptchatbot.data.Gpt3Request
import com.example.gptchatbot.data.Gpt3Resp
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Gpt3Api {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer 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"
    )
    @POST("v1/completions")
    suspend fun sendMessage(@Body request: Gpt3Request): Gpt3Resp

    companion object {
        private const val BASE_URL = "https://api.openai.com/"

        fun create(): Gpt3Api {

            val client = OkHttpClient.Builder()
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Gpt3Api::class.java)
        }
    }
}