package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryMatkul
import com.example.ucp2.ui.navigation.AlamatNavigasi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailMkViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMatkul: RepositoryMatkul
) : ViewModel() {
    private val _kode : String = checkNotNull(savedStateHandle[AlamatNavigasi.DestinasiMkDetail.kode])

    val detailMkUiState: StateFlow<DetailMkUiState> = repositoryMatkul.getMataKuliah(_kode)
        .filterNotNull()
        .map {
            DetailMkUiState(
                detailMkUiEvent = it.toDetailMkUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailMkUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailMkUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: " Terjadi Kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailMkUiState(
                isLoading = true
            )
        )
    fun deleteMataKuliah(){
        detailMkUiState.value.detailMkUiEvent.toMataKuliahEntity().let {
            viewModelScope.launch {
                repositoryMatkul.deleteMataKuliah(it)
            }
        }
    }
}

data class DetailMkUiState(
    val detailMkUiEvent: MataKuliahEvent = MataKuliahEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEventEmpty : Boolean
        get() = detailMkUiEvent == MataKuliahEvent()

    val isUiEventNotEmpty :Boolean
        get() = detailMkUiEvent != MataKuliahEvent()
}
fun MataKuliah.toDetailMkUiEvent(): MataKuliahEvent{
    return MataKuliahEvent(
        kode = kode,
        namaMk = namaMk,
        sks = sks,
        semester = semester,
        jenisMK = jenisMK,
        DosenPengampu = DosenPengampu
    )
}
