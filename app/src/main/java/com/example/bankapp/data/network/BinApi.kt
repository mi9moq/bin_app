package com.example.bankapp.data.network

import retrofit2.http.GET

interface BinApi {
    @GET("42762600")
    suspend fun loadData():BinResponse
}