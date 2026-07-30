package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.BearingHubApplication
import com.example.model.Bearing
import com.example.model.BearingData
import com.example.model.Inventory
import com.example.repository.SearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: SearchRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Empty)
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _isOwnerModalOpen = MutableStateFlow(false)
    val isOwnerModalOpen: StateFlow<Boolean> = _isOwnerModalOpen.asStateFlow()

    private val _editingBearing = MutableStateFlow<Bearing?>(null)
    val editingBearing: StateFlow<Bearing?> = _editingBearing.asStateFlow()

    private val _editingInventory = MutableStateFlow<Inventory?>(null)
    val editingInventory: StateFlow<Inventory?> = _editingInventory.asStateFlow()

    fun onQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery.uppercase()
    }

    fun search() {
        val trimmedQuery = _searchQuery.value.trim().uppercase()
        if (trimmedQuery.isEmpty()) {
            return
        }

        _uiState.value = SearchUiState.Loading

        viewModelScope.launch {
            val result = repository.searchBearing(trimmedQuery)
            result.fold(
                onSuccess = { response ->
                    if (response.success && response.data != null) {
                        _uiState.value = SearchUiState.Success(response.data)
                    } else {
                        _uiState.value = SearchUiState.NotFound(trimmedQuery)
                    }
                },
                onFailure = { throwable ->
                    _uiState.value = SearchUiState.Error(
                        throwable.localizedMessage ?: "Network error. Please try again."
                    )
                }
            )
        }
    }

    fun openEditDialog(bearing: Bearing?, inventory: Inventory?) {
        _editingBearing.value = bearing
        _editingInventory.value = inventory
        _isOwnerModalOpen.value = true
    }

    fun closeEditDialog() {
        _isOwnerModalOpen.value = false
        _editingBearing.value = null
        _editingInventory.value = null
    }

    fun saveBearing(bearing: Bearing, inventory: Inventory) {
        viewModelScope.launch {
            repository.saveBearingDetails(bearing, listOf(inventory))
            closeEditDialog()

            // Update search bar and refresh view with updated data immediately
            _searchQuery.value = bearing.number
            _uiState.value = SearchUiState.Success(
                BearingData(
                    bearing = bearing,
                    inventory = listOf(inventory)
                )
            )
        }
    }

    fun retry() {
        search()
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _uiState.value = SearchUiState.Empty
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BearingHubApplication)
                SearchViewModel(repository = application.container.searchRepository)
            }
        }
    }
}
