package com.example.bankapp.data.network

import com.google.gson.annotations.SerializedName

data class CountryDto(
    val numeric: String,
    @SerializedName("alpha2")
    val alphaTwo: String,
    val name: String,
    val emoji: String,
    val currency: String,
    val latitude: Int,
    val longitude: Int,
)
