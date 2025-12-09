package com.example.f1widget.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // 1. The Secure Base URL (Note the 's' in https)
    private const val BASE_URL = "https://api.jolpi.ca/ergast/f1/"

    // 2. The Builder
    // This creates the Retrofit object and tells it how to parse JSON
    val api: F1ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(F1ApiService::class.java)
    }
}