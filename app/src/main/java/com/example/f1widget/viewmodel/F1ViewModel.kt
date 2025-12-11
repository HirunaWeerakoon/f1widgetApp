package com.example.f1widget.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.f1widget.model.ConstructorStanding
import com.example.f1widget.model.DriverStanding
import com.example.f1widget.network.RetrofitInstance
import kotlinx.coroutines.launch
import com.example.f1widget.repository.F1Repository

class F1ViewModel(private val repository: F1Repository) : ViewModel() {
    // 1. State: What the UI sees
    // We use a private _state (mutable) and a public state (immutable)
    private val _drivers = mutableStateOf<List<DriverStanding>>(emptyList())
    val drivers: State<List<DriverStanding>> = _drivers

    private val _constructors=mutableStateOf<List<ConstructorStanding>>(emptyList())
    val constructors:State<List<ConstructorStanding>> =_constructors

    private val _selectedTab=mutableStateOf(0)
    val selectTab:State<Int> =_selectedTab

    // 2. Loading State (To show a spinner)
    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    // 3. Error State (To show "No Internet")
    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    init {
        // Automatically fetch data when the ViewModel is created
        fetchData()
    }
    // Call this when the user clicks the Tab
    fun changeTab(index: Int) {
        _selectedTab.value = index
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                if (_selectedTab.value == 0) {
                    // OLD: val response = RetrofitInstance.api.getDriverStandings()...

                    // NEW: Ask the Repository
                    val list = repository.getDrivers()
                    _drivers.value = list

                } else {
                    // For now, keep the old way for Constructors
                    // (unless you added caching for them too!)
                    val response = RetrofitInstance.api.getConstructorStandings()
                    val list = response.mrData.standingsTable.standingsLists.firstOrNull()?.constructorStandings ?: emptyList()
                    _constructors.value = list
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // 1. Get the Application Context
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as android.app.Application)

                // 2. Get the Database & DAO
                val database = com.example.f1widget.data.F1Database.getDatabase(application)
                val dao = database.f1Dao()

                // 3. Create the Repository
                val repository = F1Repository(dao)

                // 4. Return the ViewModel with the repo
                F1ViewModel(repository)
            }
        }
    }
}