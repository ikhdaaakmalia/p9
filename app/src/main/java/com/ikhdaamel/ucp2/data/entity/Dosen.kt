package com.ikhdaamel.ucp2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Dosen")
data class Dosen(
    @PrimaryKey
    val nidn: String,
    val nama: String,
    val jeniKelamin: String
)
