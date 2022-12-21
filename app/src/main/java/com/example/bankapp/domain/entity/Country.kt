package com.example.bankapp.domain.entity

data class Country(
    val numeric: String,
    val alphaTwo: String,
    val name: String,
    val emoji: String,
    val currency: String,
    val latitude: Int,
    val longitude: Int,
)
