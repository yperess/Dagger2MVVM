package com.example.dagger2mvvm.repository.common

import retrofit2.Response
import java.io.IOException

/**
 *
 */
class ApiResponse<T> {

    val code: Int
    val body: T?
    val errorMessage: String?

    constructor(error: Throwable) {
        code = 500
        body = null
        errorMessage = error.message
    }

    constructor(response: Response<T>) {
        code = response.code()
        if (response.isSuccessful) {
            body = response.body()
            errorMessage = null
        } else {
            body = null
            var message = response.errorBody()?.let {
                try { it.string() }
                catch (e: IOException) { null }
            }
            if (message.isNullOrEmpty()) {
                message = response.message()
            }
            errorMessage = message
        }
    }

    fun isSuccessful() = code in 200..299
}