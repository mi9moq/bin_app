package com.example.bankapp.domain.usecase

import com.example.bankapp.domain.entity.BinInfo
import com.example.bankapp.domain.repository.BinRepository

class LoadDataUseCase(private val repository: BinRepository) {
    suspend fun loadData(bin :String): BinInfo? {
        return repository.loadData(bin)
    }
}