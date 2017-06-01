package com.example.dagger2mvvm.ui.main

import android.os.Bundle
import com.example.dagger2mvvm.R
import com.example.dagger2mvvm.base.BaseActivity

class MainActivity : BaseActivity<MainActivityViewModel>() {

    override val viewModelClass: Class<MainActivityViewModel> = MainActivityViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
