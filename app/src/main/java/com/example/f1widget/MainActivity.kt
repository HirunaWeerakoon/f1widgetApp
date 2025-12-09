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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create the ViewModel
        val viewModel = F1ViewModel()

        setContent {
            F1WidgetTheme { // Use your theme name
                DriverListScreen(viewModel = viewModel)
            }
        }
    }
}

