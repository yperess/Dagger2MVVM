package com.example.dagger2mvvm

import android.app.Activity
import android.app.Application
import com.example.dagger2mvvm.di.AppModule
import com.example.dagger2mvvm.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 *
 */
class App: Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
                .inject(this)
    }

    //
    // HasActivityInjector
    //

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector
}