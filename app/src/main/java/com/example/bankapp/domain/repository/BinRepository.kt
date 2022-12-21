package com.example.bankapp.domain.repository

import com.example.bankapp.domain.entity.BinInfo

interface BinRepository {
    suspend fun loadData():BinInfo
}