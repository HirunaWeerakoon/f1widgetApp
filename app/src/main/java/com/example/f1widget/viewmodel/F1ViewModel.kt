package com.example.f1widget.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f1widget.model.DriverStanding
import com.example.f1widget.network.RetrofitInstance
import kotlinx.coroutines.launch

class F1ViewModel : ViewModel() {

    // 1. State: What the UI sees
    // We use a private _state (mutable) and a public state (immutable)
    private val _drivers = mutableStateOf<List<DriverStanding>>(emptyList())
    val drivers: State<List<DriverStanding>> = _drivers

    // 2. Loading State (To show a spinner)
    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    // 3. Error State (To show "No Internet")
    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    init {
        // Automatically fetch data when the ViewModel is created
        fetchStandings()
    }

    private fun fetchStandings() {
        // Launch a Coroutine (The Background Worker)
        viewModelScope.launch {
            try {
                // ??? CALL THE API HERE ???
                // Hint: Use RetrofitInstance.api.getDriverStandings()
                // Store the result in a variable named 'response'
                var response = RetrofitInstance.api.getDriverStandings()




                // ??? UPDATE THE STATE ???
                // Hint: Drill down into the response to find the list.
                // response.mrData.standingsTable.standingsLists[0].driverStandings
                // Assign that list to _drivers.value
                _drivers.value = response.mrData.standingsTable.standingsLists[0].driverStandings
                val list = response.mrData.standingsTable.standingsLists.firstOrNull()
                _drivers.value = list?.driverStandings ?: emptyList()



                // Stop loading
                _isLoading.value = false

            } catch (e: Exception) {
                // Something broke (No internet, Server down)
                _error.value = "Error: ${e.message}"
                _isLoading.value = false
            }
        }
    }
}