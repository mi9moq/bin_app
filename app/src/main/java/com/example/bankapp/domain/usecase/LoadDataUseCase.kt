package com.example.bankapp.domain.usecase

import com.example.bankapp.domain.entity.CardInfo
import com.example.bankapp.domain.repository.BinRepository
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(private val repository: BinRepository) {
    suspend fun loadData(bin :String): CardInfo? {
        return repository.loadData(bin)
    }
}