package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryMatkul
import com.example.ucp2.ui.navigation.AlamatNavigasi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMkViewModel(
     savedStateHandle: SavedStateHandle,
    private val repositoryMatkul: RepositoryMatkul
 ) : ViewModel(){
     var updateUiState by mutableStateOf(MkUiState())
         private set
    private val _kode: String = checkNotNull(savedStateHandle[AlamatNavigasi.DestinasiUpdate.kode])

    init {
        viewModelScope.launch {
            updateUiState = repositoryMatkul.getMataKuliah(_kode)
                .filterNotNull()
                .first()
                .toMkUiState()
        }
    }
    fun updateState(mataKuliahEvent: MataKuliahEvent){
        updateUiState = updateUiState.copy(
            mataKuliahEvent = mataKuliahEvent,
        )
    }
    fun validateFieldsMk(): Boolean{
        val event = updateUiState.mataKuliahEvent
        val errorState = FormErrorStateMK(
            kode = if (event.kode.isNotEmpty()) null else "Kode Tidak Boleh Kosong",
            namaMk = if (event.kode.isNotEmpty()) null else "Nama Tidak Boleh Kosong",
            sks = if (event.kode.isNotEmpty()) null else "SKS Tidak Boleh Kosong",
            semester = if (event.kode.isNotEmpty()) null else "Semester Tidak Boleh Kosong",
            jenisMK = if (event.kode.isNotEmpty()) null else "jenis  Mata Kuliah Tidak Boleh Kosong",
            DosenPengampu = if (event.kode.isNotEmpty()) null else "Dosen Pengampu Tidak Boleh Kosong",
        )

        updateUiState = updateUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData(
        DosenPengampu: String
    ){
        val currentEvent = updateUiState.mataKuliahEvent

        if (validateFieldsMk()){
            viewModelScope.launch {
                try {
                    println(" Memperbarui data : $currentEvent")

                    repositoryMatkul.updateMataKuliah(currentEvent.toMataKuliahEntity())
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data Berhasil Diupdate",
                        mataKuliahEvent = MataKuliahEvent(),
                        isEntryValid = FormErrorStateMK()
                    )
                    println("snackBarMessage diatur: ${updateUiState.snackBarMessage}")
                }
                catch (e: Exception){
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data Gagal Diupdate: ${e.message}"
                    )
                }
            }
        }else{
            updateUiState = updateUiState.copy(
                snackBarMessage = "Data Tidak Valid. Periksa kembali input Anda."
            )
        }
    }
    fun resetSnackBarMessage(){
        updateUiState = updateUiState.copy(snackBarMessage = null)
    }
 }

fun MataKuliah.toMkUiState(): MkUiState = MkUiState(
    mataKuliahEvent = this.toDetailMkUiEvent()
)
