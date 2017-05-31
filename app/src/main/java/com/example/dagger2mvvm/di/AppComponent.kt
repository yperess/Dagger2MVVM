package com.example.dagger2mvvm.di

import com.example.dagger2mvvm.App
import com.example.dagger2mvvm.di.main.MainActivityModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 *
 */
@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class,
        AppModule::class,
        MainActivityModule::class))
interface AppComponent {
    fun inject(app: App)
}