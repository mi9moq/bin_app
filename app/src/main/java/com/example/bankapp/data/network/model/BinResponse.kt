package com.example.bankapp.data.network.model

data class BinResponse(
    val number: NumberDto,
    val scheme: String,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val country: CountryDto?,
    val bank: BankDto?
)