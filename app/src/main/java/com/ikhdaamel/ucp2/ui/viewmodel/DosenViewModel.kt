package com.ikhdaamel.ucp2.ui.viewmodel

import com.ikhdaamel.ucp2.data.entity.Dosen

fun DosenEvent.toDosenEntity(): Dosen = Dosen(
    nidn = nidn,
    nama = nama,
    jeniKelamin = jeniKelamin
)

data class DosenEvent(
    val nidn: String ="",
    val nama: String ="",
    val jeniKelamin: String =""
)