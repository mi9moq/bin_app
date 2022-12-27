package com.example.bankapp.domain.entity

data class BinInfo(
    val id: Int = UNDEFINED_ID,
    val bin: String
){
    companion object{

        const val UNDEFINED_ID = 0
    }
}
