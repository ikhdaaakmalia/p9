package com.ikhdaamel.ucp2.ui.viewmodel.matakuliah

import com.ikhdaamel.ucp2.data.entity.MataKuliah

class DetailMatKulViewModel {
}

fun MataKuliah.toDetailUIEvent(): MataKuliahEvent{
    return MataKuliahEvent(
        kode = kode,
        nama_mk = nama_mk,
        sks = sks,
        jenis = jenis,
        dosenPengampu = dosenPengampu
    )
}