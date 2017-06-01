package com.example.dagger2mvvm.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.util.ArrayMap
import com.example.dagger2mvvm.di.viewmodel.ViewModelSubcomponent
import com.example.dagger2mvvm.ui.main.MainActivityViewModel
import javax.inject.Singleton
import kotlin.reflect.KFunction

/**
 *
 */
@Singleton
class AppViewModelFactory(viewModelSubcomponent: ViewModelSubcomponent): ViewModelProvider.Factory {
    private val creators = ArrayMap<Class<*>, KFunction<ViewModel>>()

    init {
        // TODO: Put your various models here...
        creators.put(MainActivityViewModel::class.java, viewModelSubcomponent::mainActivityModel)
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creator: KFunction<ViewModel> = creators[modelClass] ?: run {
            creators.entries.forEach {
                if (modelClass.isAssignableFrom(it.key)) {
                    return@run it.value
                }
            }
            null
        } ?: throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.call() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}