package com.example.dagger2mvvm.repository.common

/**
 *
 */
enum class Status(success: Boolean = false) {
    OK(true), LOADING(true),
    INTERNAL_ERROR, NETWORK_ERROR, AUTH_ERROR
}