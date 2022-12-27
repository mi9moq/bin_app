package com.example.bankapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bin_items")
data class BinDbModel(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val bin: String
)
