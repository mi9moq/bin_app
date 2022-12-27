package com.example.bankapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject

@Entity(tableName = "bin_items")
data class BinDbModel @Inject constructor(
    @PrimaryKey(autoGenerate = true)
    val id : UByte,
    val bin: String
)
