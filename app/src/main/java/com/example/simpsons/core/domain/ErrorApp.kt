package com.example.simpsons.core.domain

sealed class ErrorApp : Throwable() {
    object NetworkError : ErrorApp()
    object NotFoundError : ErrorApp()
    object UnknownError : ErrorApp()
}