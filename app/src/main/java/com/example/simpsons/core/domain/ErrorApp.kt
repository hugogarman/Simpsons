package com.example.simpsons.core.domain

sealed class ErrorApp : Throwable() {
    data class NetworkError : ErrorApp()
    data class NotFoundError : ErrorApp()
    data class UnknownError : ErrorApp()
}