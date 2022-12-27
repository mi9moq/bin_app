package com.example.bankapp.data.repository

import com.example.bankapp.data.database.BinListDao
import com.example.bankapp.data.mapper.BinMapper
import com.example.bankapp.data.network.BinApi
import com.example.bankapp.domain.entity.BinInfo
import com.example.bankapp.domain.entity.CardInfo
import com.example.bankapp.domain.repository.BinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class BinRepositoryImpl @Inject constructor(
    private val mapper: BinMapper,
    private val api: BinApi,
    private val binListDao: BinListDao
): BinRepository {

    override suspend fun loadData(bin: String): CardInfo? {
        var cardInfo: CardInfo? = null

        try {
            val response = mapper.mapCardInfoDtoToEntity(api.loadData(bin))
            cardInfo = response
        } catch (e: Exception) {
        }

        return cardInfo
    }

    override suspend fun saveBinRequest(bin: BinInfo) {
        binListDao.addBin(mapper.mapBinEntityToBinDbModel(bin))
    }

    override fun getBinList(): Flow<Set<BinInfo>> {
        return binListDao.getBinList().map {
            mapper.mapBindDbModelListToListEntity(it).toSet()
        }
    }
}