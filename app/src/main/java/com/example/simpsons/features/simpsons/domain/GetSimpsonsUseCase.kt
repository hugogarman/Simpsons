package com.example.simpsons.features.simpsons.domain

import com.example.simpsons.core.domain.ErrorApp

class GetSimpsonsUseCase(private val simpsonsRepository: SimpsonsRepository) {
    suspend fun execute(): Result<List<Simpson>> {
        return try {
            simpsonsRepository.getSimpsons()
        } catch (e: Exception) {
            Result.failure(ErrorApp.UnknownError)
        }
    }
}