package com.example.simpsons.features.simpsons.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class SimpsonsApiRemoteDataSource(
    private val httpClient: HttpClient
) {
    suspend fun getSimpsons(): List<SimpsonRemote> {
        return try {
            val response: SimpsonsApiResponse = httpClient.get(
                "https://thesimpsonsapi.com/api/characters"
            ).body()
            response.results
        } catch (e: Exception) {
            throw e
        }
    }
}
