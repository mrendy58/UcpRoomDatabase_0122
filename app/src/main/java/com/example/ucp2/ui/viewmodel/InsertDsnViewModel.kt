package com.example.ucp2.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.repository.RepositoryDsn
import kotlinx.coroutines.launch

class InsertDsnViewModel(private val repositoryDsn: RepositoryDsn) : ViewModel(){
    // State untuk UI
    var uiState by mutableStateOf(DsnUiState())
        private set

    // memperbaharui state berdasarkan input pengguna
    fun updateState(dosenEvent: DosenEvent){
        uiState = uiState.copy(
            dosenEvent = dosenEvent
        )
    }

    // validasi data input pengguna
    private fun validateFields():Boolean{
        val event = uiState.dosenEvent
        val errorState = FormErrorState(
            nidn = if (event.nidn.isNotEmpty()) null else "NIDN tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    //menyimpan data dosen
    fun saveData(){
        val currentEvent = uiState.dosenEvent
        if (validateFields()){
            viewModelScope.launch {
                try {
                    repositoryDsn.insertDosen(currentEvent.toDosenEntity())
                    uiState = uiState.copy(
                        snackBarMessage = " Data Berhasil Disimpan",
                        dosenEvent = DosenEvent(),//reset input form
                        isEntryValid = FormErrorState()// reset error state
                    )
                }catch (e: Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "Data Gagal Disimpan"
                    )
                }
            }
        }else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak Valid. Periksa kembali data anda"
            )
        }
    }
    //reset pesan snackbar setelah ditampilkan
    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMessage = null)
    }

}

//data class untuk uiState
data class DsnUiState(
    val listDosen: List<Dosen> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val dosenEvent: DosenEvent = DosenEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null
)

//Data Class untuk validasi form
data class FormErrorState(
    val nidn: String? = null,
    val nama: String? = null,
    val jenisKelamin: String? = null
){
    fun isValid(): Boolean{
        return nidn == null && nama == null && jenisKelamin == null
    }
}

//Data class untuk event dosen
data class DosenEvent(
    val nidn: String = " ",
    val nama: String = " ",
    val jenisKelamin: String = " "

)
//Fungsi Untuk mengoversi MhasiswaEvent ke Entity Dosen
fun DosenEvent.toDosenEntity(): Dosen = Dosen(
    nidn = nidn,
    nama = nama,
    jenisKelamin = jenisKelamin
)