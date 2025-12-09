package com.example.f1widget.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.f1widget.viewmodel.F1ViewModel

@Composable
fun DriverListScreen(viewModel: F1ViewModel) { // Takes ViewModel as input

    // Read the state from the ViewModel
    val drivers = viewModel.drivers.value
    val isLoading = viewModel.isLoading.value
    val error = viewModel.error.value

    Box(modifier = Modifier.fillMaxSize()) {

        // Scenario 1: Loading
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        // Scenario 2: Error
        else if (error != null) {
            Text(
                text = error,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Scenario 3: Success (Show the list)
        else {
            LazyColumn {
                // The magic loop
                items(drivers) { driver ->
                    DriverItem(driver = driver)
                }
            }
        }
    }
}