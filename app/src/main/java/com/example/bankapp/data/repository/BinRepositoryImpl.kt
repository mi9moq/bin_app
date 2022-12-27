package com.example.bankapp.data.repository

import com.example.bankapp.data.mapper.BinMapper
import com.example.bankapp.data.network.BinApi
import com.example.bankapp.domain.entity.BinInfo
import com.example.bankapp.domain.repository.BinRepository
import javax.inject.Inject


class BinRepositoryImpl @Inject constructor(
    private val mapper: BinMapper,
    private val api: BinApi
): BinRepository {

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