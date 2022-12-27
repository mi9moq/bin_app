package com.example.bankapp.data.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface BinListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBin(bin: BinDbModel)

    @Query("SELECT * FROM bin_items")
    fun getBinList(): Flow<List<BinDbModel>>
}