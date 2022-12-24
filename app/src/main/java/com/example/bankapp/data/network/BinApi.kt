package com.example.bankapp.data.network

import com.example.bankapp.data.network.model.BinResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BinApi {
    @GET("{bin}")
    suspend fun loadData(@Path("bin") bin:String): BinResponse
}