package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.repository.RepositoryMatkul
import kotlinx.coroutines.launch

class DosenViewModel(private val repositoryMatkul: RepositoryMatkul) : ViewModel() {

    var uiState by mutableStateOf(DosenUiState())
        private set

    init {
        fetchDosenList()
    }

    private fun fetchDosenList() {
        viewModelScope.launch {
            try {
                val dosenList = repositoryMatkul.getDosenList()
                uiState = uiState.copy(dosenList = dosenList)
            } catch (e: Exception) {
                uiState = uiState.copy(snackBarMessage = "Gagal memuat daftar dosen")
            }
        }
    }

    data class DosenUiState(
        val dosenList: List<String> = emptyList(),
        val snackBarMessage: String? = null
    )
}
