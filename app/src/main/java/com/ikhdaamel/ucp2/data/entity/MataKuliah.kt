package com.ikhdaamel.ucp2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MataKuliah")
data class MataKuliah(
    @PrimaryKey
    val kode: String,
    val nama_mk: String,
    val sks: String,
    val semester: String,
    val jenis: String,
    val dosenPengampu: String
)
