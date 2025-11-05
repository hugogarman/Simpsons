package com.example.simpsons.features.simpsons.presentation

import com.example.simpsons.features.simpsons.domain.Simpson

sealed class SimpsonsUiState {
    object Idle : SimpsonsUiState()
    object Loading : SimpsonsUiState()
    data class Success(val simpsons: List<Simpson>) : SimpsonsUiState()
    data class Failure(val message: String) : SimpsonsUiState()
}