package com.example.bankapp.data.repository

import com.example.bankapp.data.mapper.BinMapper
import com.example.bankapp.data.network.BinApi
import com.example.bankapp.domain.entity.BinInfo
import com.example.bankapp.domain.repository.BinRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://lookup.binlist.net/"

class BinRepositoryImpl : BinRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(BinApi::class.java)

    private val mapper = BinMapper()

    override suspend fun loadData(bin: String): BinInfo? {
        var binInfo: BinInfo? = null
        try {
            val response = mapper.mapBinDtoToEntity(api.loadData(bin))
            binInfo = response
        } catch (e: Exception) {
        }
        return binInfo
    }
}