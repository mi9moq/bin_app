package com.example.bankapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bankapp.R

class MainActivity : AppCompatActivity() {

    private val component by lazy { (application as BinApplication).component }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}