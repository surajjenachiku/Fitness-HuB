package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.ui.FitnessViewModel
import com.example.ui.navigation.FitnessHubApp
import com.example.ui.theme.FitnessHubTheme

class MainActivity : ComponentActivity() {

    private val fitnessViewModel: FitnessViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitnessHubTheme {
                FitnessHubApp(viewModel = fitnessViewModel)
            }
        }
    }
}
