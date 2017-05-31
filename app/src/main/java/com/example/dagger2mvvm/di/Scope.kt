package com.example.dagger2mvvm.di

import java.lang.annotation.Documented
import javax.inject.Qualifier

/**
 *
 */
@Qualifier
@Documented
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerThreadScope

@Qualifier
@Documented
@Retention(AnnotationRetention.RUNTIME)
annotation class MainThreadScope