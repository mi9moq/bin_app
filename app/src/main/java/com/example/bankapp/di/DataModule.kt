package com.example.bankapp.di

import android.app.Application
import com.example.bankapp.data.database.BinDatabase
import com.example.bankapp.data.database.BinListDao
import com.example.bankapp.data.network.ApiFactory
import com.example.bankapp.data.network.BinApi
import com.example.bankapp.data.repository.BinRepositoryImpl
import com.example.bankapp.domain.repository.BinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindBinRepository(impl: BinRepositoryImpl): BinRepository

    companion object{
        @Provides
        @ApplicationScope
        fun provideBinApi(): BinApi{
            return ApiFactory.api
        }

        @Provides
        @ApplicationScope
        fun provideBinListDao(application: Application): BinListDao{
            return BinDatabase.getInstance(application).binListDao()
        }
    }
}