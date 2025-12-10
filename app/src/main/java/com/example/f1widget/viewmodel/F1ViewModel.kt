package com.example.f1widget.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1widget.model.ConstructorStanding
import com.example.f1widget.model.DriverStanding
import com.example.f1widget.network.RetrofitInstance
import kotlinx.coroutines.launch

class F1ViewModel : ViewModel() {

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
                    // Fetch Drivers
                    val response = RetrofitInstance.api.getDriverStandings()
                    val list = response.mrData.standingsTable.standingsLists.firstOrNull()
                    _drivers.value = list?.driverStandings ?: emptyList()
                } else {
                    // Fetch Teams
                    val response = RetrofitInstance.api.getConstructorStandings()
                    val list = response.mrData.standingsTable.standingsLists.firstOrNull()
                    _constructors.value = list?.constructorStandings ?: emptyList()
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}