package com.ikhdaamel.ucp2.ui.viewmodel

import com.ikhdaamel.ucp2.data.entity.Dosen

data class FormErrorState(
    val nidn: String? = null,
    val nama: String? = null,
    val jeniKelamin: String? = null,
){
    fun isValid(): Boolean{
        return nidn == null && nama == null && jeniKelamin == null
    }
}

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