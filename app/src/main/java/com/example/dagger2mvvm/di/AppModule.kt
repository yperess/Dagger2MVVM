package com.example.dagger2mvvm.di

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.content.SharedPreferences
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.preference.PreferenceManager
import com.example.dagger2mvvm.base.AppViewModelFactory
import com.example.dagger2mvvm.di.viewmodel.ViewModelSubcomponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *
 */
@Module(subcomponents = arrayOf(ViewModelSubcomponent::class))
class AppModule(private val application: Application) {

    @Provides
    fun provideSharedPreferences(): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(application)

    @Provides
    @WorkerThreadScope
    fun provideWorkerThread(): Handler = Handler(HandlerThread("WorkerThread").apply {
        isDaemon = true
        start()
    }.looper)

    @Provides
    @MainThreadScope
    fun provideMainThread(): Handler = Handler(Looper.getMainLooper())

    @Provides
    @Singleton
    fun provideViewModelFactory(viewModelSubcomponent: ViewModelSubcomponent.Builder
            ): ViewModelProvider.Factory = AppViewModelFactory(viewModelSubcomponent.build())
}