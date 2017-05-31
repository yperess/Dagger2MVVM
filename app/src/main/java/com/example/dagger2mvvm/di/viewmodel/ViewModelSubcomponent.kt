package com.example.dagger2mvvm.di.viewmodel

import com.example.dagger2mvvm.main.MainActivityViewModel
import dagger.Subcomponent

/**
 *
 */
@Subcomponent
interface ViewModelSubcomponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubcomponent
    }

    // TODO: Add the various models here.
    fun mainActivityModel(): MainActivityViewModel
}