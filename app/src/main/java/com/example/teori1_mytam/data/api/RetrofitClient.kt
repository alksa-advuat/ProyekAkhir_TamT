package com.example.teori1_mytam.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    private const val GIST_URL =
        "https://gist.githubusercontent.com/alksa-advuat/f3b29a227359864454c5fcad634ed7be/raw/"

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(GIST_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // Untuk auth (MockAPI)
    private const val AUTH_URL =
        "https://6a01746236fb6ad04de0f2e5.mockapi.io/api/tam/"

    val authInstance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(AUTH_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}