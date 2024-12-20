package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryMatkul
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InsertMkViewModel(private val repositoryMatkul: RepositoryMatkul) : ViewModel() {
    // State untuk UI
    var uiState by mutableStateOf(MkUiState())


    // Memperbarui state berdasarkan input pengguna
    fun updateState(mataKuliahEvent: MataKuliahEvent) {
        uiState = uiState.copy(
            mataKuliahEvent = mataKuliahEvent
        )
    }
    val mkUiState: StateFlow<MkUiState> = repositoryMatkul.getALLMataKuliah()
        .filterNotNull()
        .map {
            MkUiState(
                listMk = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(MkUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                MkUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = MkUiState(
                isLoading = true,
            )
        )

    // Validasi data input pengguna
    private fun validateFields(): Boolean {
        val event = uiState.mataKuliahEvent
        val errorState = FormErrorStateMK(
            kode = if (event.kode.isNotEmpty()) null else "Kode Tidak Boleh Kosong",
            namaMk = if (event.namaMk.isNotEmpty()) null else "Nama Mata Kuliah Tidak Boleh Kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS Tidak Boleh Kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester Tidak Boleh Kosong",
            jenisMK = if (event.jenisMK.isNotEmpty()) null else "Jenis Mata Kuliah Tidak Boleh Kosong",
            DosenPengampu = if (event.DosenPengampu.isNotEmpty()) null else "Dosen Pengampu Tidak Boleh Kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }


    // Menyimpan data Mata Kuliah
    fun saveData() {
        val currentEvent = uiState.mataKuliahEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMatkul.insertMataKuliah(currentEvent.toMataKuliahEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil Disimpan",
                        mataKuliahEvent = MataKuliahEvent(), // Reset input form
                        isEntryValid = FormErrorStateMK() // Reset error state
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data Gagal Disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data Anda"
            )
        }
    }

 // Menghapus pesan SnackBar
    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}

// Data class untuk UI state
data class MkUiState(
    val listMk: List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    val isEntryValid: FormErrorStateMK = FormErrorStateMK(),
    val snackBarMessage: String? = null
)

// Data class untuk validasi form
data class FormErrorStateMK(
    val kode: String? = null,
    val namaMk: String? = null,
    val sks: String? = null,
    val semester: String? = null,
    val jenisMK: String? = null,
    val DosenPengampu: String? = null
) {
    fun isValid(): Boolean {
        return kode == null && namaMk == null && sks == null && semester == null &&
                jenisMK == null && DosenPengampu == null
    }
}

// Data class untuk event Mata Kuliah
data class MataKuliahEvent(
    val kode: String = "",
    val namaMk: String = "",
    val sks: String = "",
    val semester: String = "",
    val jenisMK: String = "",
    val DosenPengampu: String = ""
)

// Fungsi untuk mengonversi MataKuliahEvent ke Entity MataKuliah
fun MataKuliahEvent.toMataKuliahEntity(): MataKuliah = MataKuliah(
    kode = kode,
    namaMk = namaMk,
    sks = sks,
    semester = semester,
    jenisMK = jenisMK,
    DosenPengampu = DosenPengampu
)