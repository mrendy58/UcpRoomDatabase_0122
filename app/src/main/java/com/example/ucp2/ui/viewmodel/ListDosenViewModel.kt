package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.repository.RepositoryDsn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class ListDosenViewModel(
    private val repositoryDsn: RepositoryDsn
) : ViewModel() {
    val dosenListUiState: StateFlow<DosenListUiState> = repositoryDsn.getALLDosen()
        .filterNotNull()
        .map {
            DosenListUiState(
                listDosen = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DosenListUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(DosenListUiState(
                isLoading = false,
                isError = true,
                errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DosenListUiState(isLoading = true)
        )
}
data class DosenListUiState(
    val listDosen: List<Dosen> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)
