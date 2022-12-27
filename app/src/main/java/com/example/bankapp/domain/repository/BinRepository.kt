package com.example.bankapp.domain.repository

import com.example.bankapp.domain.entity.BinInfo
import com.example.bankapp.domain.entity.CardInfo
import kotlinx.coroutines.flow.Flow

interface BinRepository {
    suspend fun loadData(bin: String):CardInfo?

    suspend fun saveBinRequest(bin: BinInfo)

    fun getBinList():Flow<List<BinInfo>>
}