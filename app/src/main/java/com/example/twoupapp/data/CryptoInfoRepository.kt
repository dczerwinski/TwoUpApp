package com.example.twoupapp.data

import com.example.twoupapp.network.Endpoint

class CryptoInfoRepository(
    private val service: Endpoint
) {
    suspend fun getCryptoInfoData() = service.getData()
}
