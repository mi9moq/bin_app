package com.example.bankapp.domain.usecase

import com.example.bankapp.domain.entity.BinInfo
import com.example.bankapp.domain.repository.BinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBinListUseCase @Inject constructor(private val repository: BinRepository) {
    fun getBinList(): Flow<Set<BinInfo>> {
        return repository.getBinList()
    }
}