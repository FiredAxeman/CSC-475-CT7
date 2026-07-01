package com.example.csc_475_ct7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.csc_475_ct7.presentation.ConverterViewModel
import com.example.csc_475_ct7.presentation.navigation.Route
import com.example.csc_475_ct7.presentation.ui.ConverterScreen
import com.example.csc_475_ct7.ui.theme.CSC475CT7Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CSC475CT7Theme {
                val backStack = rememberNavBackStack(Route.Converter)
                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryProvider = { key ->
                        when (key) {
                            is Route.Converter -> {
                                NavEntry(key) {
                                    val viewModel: ConverterViewModel = viewModel()
                                    ConverterScreen(viewModel = viewModel)
                                }
                            }
                            else -> error("Unknown route: $key")
                        }
                    }
                )
            }
        }
    }
}
