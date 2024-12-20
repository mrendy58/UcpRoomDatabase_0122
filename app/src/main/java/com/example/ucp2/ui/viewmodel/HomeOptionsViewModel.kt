package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryDsn
import com.example.ucp2.repository.RepositoryMatkul
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// ViewModel untuk halaman Home
class HomeOptionsViewModel (
    private val repositoryDsn: RepositoryDsn,
    private val repositoryMatkul: RepositoryMatkul
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeOptionsState())
    val uiState: StateFlow<HomeOptionsState> = _uiState

    init {
        fetchData()
    }

    private fun fetchData() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val dosenlist = repositoryDsn.getALLDosen().first()
                val mataKuliahList = repositoryMatkul.getALLMataKuliah().first()

                _uiState.value = _uiState.value.copy(
                    dosenList = dosenlist,
                    mataKuliahList = mataKuliahList,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Terjadi kesalahan saat memuat data"
                )
            }
        }
    }
}
// Data class untuk state UI pada halaman Home
data class HomeOptionsState(
    val dosenList: List<Dosen> = emptyList(),
    val mataKuliahList: List<MataKuliah> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)
