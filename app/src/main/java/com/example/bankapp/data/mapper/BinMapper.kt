package com.example.bankapp.data.mapper

import com.example.bankapp.data.network.BankDto
import com.example.bankapp.data.network.BinResponse
import com.example.bankapp.data.network.CountryDto
import com.example.bankapp.domain.entity.Bank
import com.example.bankapp.domain.entity.BinInfo
import com.example.bankapp.domain.entity.Country

class BinMapper {

    private fun mapBankDtoToEntity(bank: BankDto?) = bank?.let {
        Bank(
            name = it.name,
            url = it.url,
            phone = it.phone,
            city = it.city
        )
    }

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
        number = bin.number,
        scheme = bin.scheme,
        type = bin.type,
        brand = bin.brand,
        prepaid = bin.prepaid,
        country = mapCountryDtoToEntity(bin.country),
        bank = mapBankDtoToEntity(bin.bank)
    )
}