package com.example.simpsons.features.simpsons.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class SimpsonRemote(
    val id: Int,
    val name: String,
    val age: Int?,  // ← Cambiar a Int? (nullable)
    val occupation: String,
    val portrait_path: String
)