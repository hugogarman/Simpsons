package com.example.simpsons.features.simpsons.data.remote

import com.example.simpsons.features.simpsons.domain.Simpson

fun SimpsonRemote.toDomainModel(): Simpson {

    val fullImageUrl = "https://cdn.thesimpsonsapi.com/500${this.portrait_path}"

    return Simpson(
        id = this.id,
        name = this.name,
        age = this.age ?: 0,
        occupation = this.occupation,
        portraitPath = fullImageUrl,
        phrases = this.phrases
    )
}