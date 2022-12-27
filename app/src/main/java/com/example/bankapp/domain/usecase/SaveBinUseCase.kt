package com.example.bankapp.domain.usecase

import com.example.bankapp.domain.entity.BinInfo
import com.example.bankapp.domain.repository.BinRepository
import javax.inject.Inject

class SaveBinUseCase @Inject constructor(private val repository: BinRepository) {
    suspend fun saveBin(bin: BinInfo){
        repository.saveBinRequest(bin)
    }
}