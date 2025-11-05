package com.example.simpsons.features.simpsons.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class SimpsonRemote(
    val id: Int,
    val name: String,
    val age: Int?,
    val occupation: String,
    val portrait_path: String,
    val phrases: List<String>
)