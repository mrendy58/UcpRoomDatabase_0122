package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryMatkul

class InsertMkViewModel(private val repositoryMatkul: RepositoryMatkul) : ViewModel(){

}

//fungsi untuk mengkonversi MataKuliahEvent ke Entity matakuliah
fun MataKuliahEvent.toMataKuliahEntity(): MataKuliah = MataKuliah(
    kode = kode,
    namaMk = namaMk,
    sks = sks,
    semester = semester,
    jenisMK = jenisMK,
    DosenPengampu = DosenPengampu
)