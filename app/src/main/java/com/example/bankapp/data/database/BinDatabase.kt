package com.example.bankapp.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BinDbModel::class], version = 1)
abstract class BinDatabase: RoomDatabase() {
    abstract fun binListDao(): BinListDao

    companion object{

        private var instance: BinDatabase? = null
        private val lock = Any()
        private const val DB_NAME = "bins_list.db"

        fun getInstance(application: Application): BinDatabase{
            instance?.let {
                return it
            }
            synchronized(lock){
                instance?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    BinDatabase::class.java,
                    DB_NAME
                ).build()
                instance = db
                return db
            }
        }
    }
}