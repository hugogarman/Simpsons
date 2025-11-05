package com.example.simpsons.features.simpsons.data

import com.example.simpsons.core.domain.ErrorApp
import com.example.simpsons.features.simpsons.data.remote.SimpsonsApiRemoteDataSource
import com.example.simpsons.features.simpsons.data.remote.toDomainModel
import com.example.simpsons.features.simpsons.domain.Simpson
import com.example.simpsons.features.simpsons.domain.SimpsonsRepository

class SimpsonsDataRepository(private val simpsonsApiRemoteDataSource: SimpsonsApiRemoteDataSource) :
    SimpsonsRepository {
    override suspend fun getSimpsons(): Result<List<Simpson>> {
        return try {
            val simpsonsRemote = simpsonsApiRemoteDataSource.getSimpsons()
            val simpsons = simpsonsRemote.map { simpsonRemote ->
                simpsonRemote.toDomainModel()
            }
            Result.success(simpsons)
        } catch (e: Exception) {
            val appError = when {
                e.message?.contains("Network") == true -> ErrorApp.NetworkError
                e.message?.contains("404") == true -> ErrorApp.NotFoundError
                else -> ErrorApp.UnknownError
            }
            Result.failure(appError)
        }
    }
}