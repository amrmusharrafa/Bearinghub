package com.example.viewmodel

import com.example.model.BearingData

sealed interface SearchUiState {
    object Empty : SearchUiState
    object Loading : SearchUiState
    data class Success(val bearingData: BearingData) : SearchUiState
    data class NotFound(val searchedNumber: String) : SearchUiState
    data class Error(val message: String) : SearchUiState
}
