package com.example.ucp2.ui.viewmodel

import com.example.ucp2.data.entity.MataKuliah

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
