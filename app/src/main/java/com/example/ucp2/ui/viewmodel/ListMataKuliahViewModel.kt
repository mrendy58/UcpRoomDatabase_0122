package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryMatkul
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class ListMataKuliahViewModel(
    private val repositoryMatkul: RepositoryMatkul
) : ViewModel() {

    val mkListUiState: StateFlow<MkListUiState> = repositoryMatkul.getALLMataKuliah()
        .filterNotNull()
        .map { mataKuliahList ->
            MkListUiState(
                listMk = mataKuliahList.toList(),
                isLoading = false
            )
        }
        .onStart {
            emit(MkListUiState(isLoading = true))
            delay(900)
        }
        .catch { exception ->
            emit(
                MkListUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = exception.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MkListUiState(isLoading = true)
        )
}

data class MkListUiState(
    val listMk: List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)
