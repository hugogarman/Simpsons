package com.example.simpsons.features.simpsons.domain

interface SimpsonsRepository {
    suspend fun getSimpsons(): Result<List<Simpson>>
}