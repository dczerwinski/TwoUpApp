package com.example.twoupapp.network

import com.example.twoupapp.data.CryptoInfo
import retrofit2.Response
import retrofit2.http.GET

interface Endpoint {

    @GET("/api/tickers/?limit=20")
    suspend fun getData(): Response<CryptoInfo>
}
