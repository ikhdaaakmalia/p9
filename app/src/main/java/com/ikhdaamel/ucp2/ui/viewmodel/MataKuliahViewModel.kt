package com.ikhdaamel.ucp2.ui.viewmodel

import com.ikhdaamel.ucp2.data.entity.MataKuliah

fun MataKuliahEvent.toMataKuliahEntity(): MataKuliah = MataKuliah(
    kode = kode,
    nama_mk = nama_mk,
    sks = sks,
    semester = semester,
    jenis = jenis,
    dosenPengampu = dosenPengampu
)

data class MataKuliahEvent(
    val kode: String ="",
    val nama_mk: String ="",
    val sks: String ="",
    val semester: String ="",
    val jenis: String ="",
    val dosenPengampu: String =""
)