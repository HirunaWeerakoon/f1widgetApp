package com.example.f1widget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.f1widget.presentation.DriverListScreen
import com.example.f1widget.ui.theme.F1WidgetTheme
import com.example.f1widget.viewmodel.F1ViewModel
import androidx.activity.viewModels
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.f1widget.worker.F1WidgetWorker
import java.util.concurrent.TimeUnit
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder

class MainActivity : ComponentActivity() {

    private val viewModel: F1ViewModel by viewModels { F1ViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBackgroundSync()

        setContent {
            F1WidgetTheme { // Use your theme name
                DriverListScreen(viewModel = viewModel)
            }
        }
    }
    private fun setupBackgroundSync() {
        // Define the work: Run every 15 minutes (Minimum allowed by Android)
        // In production, maybe every 6 or 12 hours is better to save battery.
        val workRequest = PeriodicWorkRequestBuilder<F1WidgetWorker>(
            15, TimeUnit.MINUTES
        ).build()

        // Enqueue it
        // KEEP means: If it's already scheduled, don't replace it (avoids duplicates)
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "F1WidgetUpdate",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}


