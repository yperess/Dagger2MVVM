package com.example.dagger2mvvm.base

import android.arch.lifecycle.*
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 *
 */
abstract class BaseActivity<T: ViewModel>: AppCompatActivity(), LifecycleRegistryOwner {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var model: T

    protected abstract val viewModelClass: Class<T>

    private val lifecycleRegistry: LifecycleRegistry by lazy { LifecycleRegistry(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        model = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
    }

    //
    // LifecycleRegistryOwner
    //

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry
}