package com.example.twoupapp.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder().apply {
        baseUrl("https://api.coinlore.com/")
        addConverterFactory(GsonConverterFactory.create())
        client(client)
    }.build()

    fun <T> buildService(service: Class<T>): T = retrofit.create(service)
}
