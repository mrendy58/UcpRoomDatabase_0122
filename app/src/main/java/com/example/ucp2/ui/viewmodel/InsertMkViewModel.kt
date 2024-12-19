package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.NotificationCompat.MessagingStyle.Message
import androidx.lifecycle.ViewModel
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryMatkul

class InsertMkViewModel(private val repositoryMatkul: RepositoryMatkul) : ViewModel(){
    //State untuk ui
    var uiState by mutableStateOf(MkUiState())
        private set

    //memperbaharui state berdasarkan input pengguna
    fun updateState(mataKuliahEvent: MataKuliahEvent){
        uiState = uiState.copy(
            mataKuliahEvent = mataKuliahEvent
        )
    }
}

// data class untuk ui state
data class MkUiState(
    val mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null
)

// data class untuk validasi form
data class FormErrorState(
    val kode: String? = null,
    val namaMk: String? = null,
    val sks: String? = null,
    val semester: String? = null,
    val jenisMK: String? = null,
    val DosenPengampu: String? = null
){
    fun isValid(): Boolean{
        return kode == null && namaMk == null && sks == null && semester == null &&
                jenisMK == null && DosenPengampu == null
    }
}
// Data Class untuk event matakuliah
data class MataKuliahEvent(
    val kode: String = "",
    val namaMk: String = "",
    val sks: String = "",
    val semester: String = "",
    val jenisMK: String = "",
    val DosenPengampu: String = ""
)
//fungsi untuk mengkonversi MataKuliahEvent ke Entity matakuliah
fun MataKuliahEvent.toMataKuliahEntity(): MataKuliah = MataKuliah(
    kode = kode,
    namaMk = namaMk,
    sks = sks,
    semester = semester,
    jenisMK = jenisMK,
    DosenPengampu = DosenPengampu
)