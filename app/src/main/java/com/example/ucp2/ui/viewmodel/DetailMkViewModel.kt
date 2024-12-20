package com.example.ucp2.ui.viewmodel

import com.example.ucp2.data.entity.MataKuliah

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
