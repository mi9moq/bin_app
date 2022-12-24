package com.example.bankapp.di

import android.app.Application
import com.example.bankapp.presentation.BinApplication
import com.example.bankapp.presentation.MainActivity
import com.example.bankapp.presentation.MainFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules =[
        DataModule::class,
    ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(application: BinApplication)

    fun inject(activity:MainActivity)

    fun inject(fragment: MainFragment)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ):ApplicationComponent
    }
}