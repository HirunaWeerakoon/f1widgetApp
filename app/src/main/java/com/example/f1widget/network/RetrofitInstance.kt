package com.example.f1widget.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private const val BASE_URL = "https://api.jolpi.ca/ergast/f1/"

    // 1. Create a Client with longer timeouts
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    // 2. Attach the client to Retrofit
    val api: F1ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // <--- Add this line
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(F1ApiService::class.java)
    }
}