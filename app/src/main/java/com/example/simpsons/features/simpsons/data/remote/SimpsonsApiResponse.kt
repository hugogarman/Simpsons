package com.example.simpsons.features.simpsons.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class SimpsonsApiResponse(
    val results: List<SimpsonRemote>
)