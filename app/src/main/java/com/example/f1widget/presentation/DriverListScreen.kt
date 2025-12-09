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
import androidx.compose.ui.tooling.preview.Preview
import com.example.f1widget.model.Driver
import com.example.f1widget.model.DriverStanding
import com.example.f1widget.model.Constructor
import com.example.f1widget.viewmodel.F1ViewModel

// 1. THE SMART SCREEN (Stateful)
// This is what MainActivity calls. It talks to the VM.
// You cannot preview this easily because of the VM.
@Composable
fun DriverListScreen(viewModel: F1ViewModel) {
    val drivers = viewModel.drivers.value
    val isLoading = viewModel.isLoading.value
    val error = viewModel.error.value

    // Pass pure data to the dumb content
    DriverListContent(
        drivers = drivers,
        isLoading = isLoading,
        error = error
    )
}

// 2. THE DUMB CONTENT (Stateless)
// This takes basic variables (List, Boolean, String).
// It doesn't know what a "ViewModel" or "Internet" is.
// WE CAN PREVIEW THIS!
@Composable
fun DriverListContent(
    drivers: List<DriverStanding>,
    isLoading: Boolean,
    error: String?
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (error != null) {
            Text(
                text = error,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn {
                items(drivers) { driver ->
                    DriverItem(driver = driver)
                }
            }
        }
    }
}

// 3. THE PREVIEW
// Now we can create fake data and verify the layout without running the app.
@Preview(showBackground = true)
@Composable
fun DriverListPreview() {
    // Create fake data for the preview
    val mockDriver = Driver(
        driverId = "1",
        permanentNumber = "1",
        givenName = "Max",
        familyName = "Verstappen",
        nationality = "Dutch"
    )
    val mockConstructor = Constructor(constructorId = "rb", name = "Red Bull Racing")

    val mockStanding = DriverStanding(
        position = "1",
        points = "300",
        driverInfo = mockDriver,
        constructors = listOf(mockConstructor)
    )

    // Render the dumb content with a fake list
    DriverListContent(
        drivers = listOf(mockStanding, mockStanding, mockStanding),
        isLoading = false,
        error = null
    )
}