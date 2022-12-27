package com.example.bankapp.domain.entity

data class BinInfo(
    val id: UByte = UNDEFINED_ID,
    val bin: String
){
    companion object{

        const val UNDEFINED_ID: UByte = 0u
    }
}
