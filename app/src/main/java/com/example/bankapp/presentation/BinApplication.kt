package com.example.bankapp.presentation

import android.app.Application
import com.example.bankapp.di.DaggerApplicationComponent

class BinApplication : Application() {

    val component by lazy { DaggerApplicationComponent.factory().create(this) }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}