package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

// ViewModel untuk halaman Home
class HomeOptionsViewModel : ViewModel() {

    // State untuk menu utama
    var uiState by mutableStateOf(HomeOptionsState())
        private set

    // Fungsi untuk menangani navigasi atau state lainnya (misalnya memilih menu)
    fun updateMenuSelection(menu: String) {
        uiState = uiState.copy(selectedMenu = menu)
    }
}

// Data class untuk state UI pada halaman Home
data class HomeOptionsState(
    val selectedMenu: String = ""
)
