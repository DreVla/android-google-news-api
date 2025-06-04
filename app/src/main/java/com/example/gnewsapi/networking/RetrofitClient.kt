package com.example.gnewsapi.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit Singleton object used to create the Retrofit instance.
 */
object RetrofitClient {
    private const val BASE_URL = "https://newsapi.org/"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}