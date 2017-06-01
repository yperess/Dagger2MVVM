package com.example.dagger2mvvm.repository.common

/**
 *
 */
data class Resource<out T>(
    val status: Status = Status.OK,
    val data: T? = null,
    val message: String? = null)