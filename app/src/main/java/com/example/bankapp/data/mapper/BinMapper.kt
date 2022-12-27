package com.example.bankapp.data.mapper

import com.example.bankapp.data.database.BinDbModel
import com.example.bankapp.data.network.model.BankDto
import com.example.bankapp.data.network.model.CardInfoDto
import com.example.bankapp.data.network.model.CountryDto
import com.example.bankapp.data.network.model.NumberDto
import com.example.bankapp.domain.entity.*
import com.example.bankapp.domain.entity.Number
import javax.inject.Inject

class BinMapper @Inject constructor() {

    private fun mapBankDtoToEntity(bank: BankDto?) = bank?.let {
        Bank(
            name = it.name,
            url = it.url,
            phone = it.phone,
            city = it.city
        )
    }

    private fun mapNumberDtoToEntity(number: NumberDto) = Number(
        length = number.length,
        luhn = number.luhn
    )

    private fun mapCountryDtoToEntity(country: CountryDto?) = country?.let {
        Country(
            numeric = it.numeric,
            alphaTwo = it.alphaTwo,
            name = it.name,
            emoji = it.emoji,
            currency = it.currency,
            latitude = it.latitude,
            longitude = it.longitude
        )
    }

    fun mapCardInfoDtoToEntity(cardInfo: CardInfoDto) = CardInfo(
        number = mapNumberDtoToEntity(cardInfo.number),
        scheme = cardInfo.scheme,
        type = cardInfo.type,
        brand = cardInfo.brand,
        prepaid = cardInfo.prepaid,
        country = mapCountryDtoToEntity(cardInfo.country),
        bank = mapBankDtoToEntity(cardInfo.bank)
    )

    fun mapBinEntityToBinDbModel(info: BinInfo) = BinDbModel(
        id = info.id,
        bin = info.bin
    )

    private fun mapBinDbModelToEntity(info:BinDbModel) = BinInfo(
        id = info.id,
        bin = info.bin
    )

    fun mapBindDbModelListToListEntity(list: List<BinDbModel>) = list.map {
        mapBinDbModelToEntity(it)
    }
}