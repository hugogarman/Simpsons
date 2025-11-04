package com.example.simpsons.features.simpsons.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpsons.core.domain.ErrorApp
import com.example.simpsons.features.simpsons.domain.GetSimpsonsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SimpsonsListViewModel(private val getSimpsonsUseCase: GetSimpsonsUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow<SimpsonsUiState>(SimpsonsUiState.Idle)
    val uiState: StateFlow<SimpsonsUiState> = _uiState

    fun loadSimpsons() {
        _uiState.value = SimpsonsUiState.Loading

        viewModelScope.launch {
            val result = getSimpsonsUseCase.execute()

            result.fold(
                onSuccess = { simpsons ->
                    _uiState.value = SimpsonsUiState.Success(simpsons)
                },
                onFailure = { error ->
                    val errorMessage = when (error) {
                        is ErrorApp.NetworkError -> "Error de conexión"
                        is ErrorApp.NotFoundError -> "No se encontraton personajes"
                        else -> "Error desconocido"
                    }
                    _uiState.value = SimpsonsUiState.Failure(errorMessage)
                }
            )
        }
    }
}