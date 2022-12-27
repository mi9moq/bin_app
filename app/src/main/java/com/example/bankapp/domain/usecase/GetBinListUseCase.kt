package com.example.bankapp.domain.usecase

import com.example.bankapp.domain.repository.BinRepository
import javax.inject.Inject

class GetBinListUseCase @Inject constructor(private val repository: BinRepository) {
    fun getBinList(){
        repository.getBinList()
    }
}