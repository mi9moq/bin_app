package com.example.bankapp.data.mapper

import com.example.bankapp.data.network.model.BankDto
import com.example.bankapp.data.network.model.BinResponse
import com.example.bankapp.data.network.model.CountryDto
import com.example.bankapp.data.network.model.NumberDto
import com.example.bankapp.domain.entity.Bank
import com.example.bankapp.domain.entity.BinInfo
import com.example.bankapp.domain.entity.Country
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

    fun mapBinDtoToEntity(bin: BinResponse) = BinInfo(
        number = mapNumberDtoToEntity(bin.number),
        scheme = bin.scheme,
        type = bin.type,
        brand = bin.brand,
        prepaid = bin.prepaid,
        country = mapCountryDtoToEntity(bin.country),
        bank = mapBankDtoToEntity(bin.bank)
    )
}