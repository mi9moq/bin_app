package com.example.bankapp.domain.entity

import com.example.bankapp.data.network.BankDto
import com.example.bankapp.data.network.NumberDto

data class BinInfo(
    val number: NumberDto,
    val scheme: String,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val country: Country?,
    val bank: Bank?
)