package com.ikhdaamel.ucp2.repository

import com.ikhdaamel.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

interface RepoMataKuliah {
    fun getAllMataKuliah(): Flow<List<MataKuliah>>
    fun getMataKuliah (kode: String) : Flow<MataKuliah>
    suspend fun insertMataKuliah (mataKuliah: MataKuliah)
    suspend fun updateMataKuliah(mataKuliah: MataKuliah)
    suspend fun deleteMataKuliah (mataKuliah: MataKuliah)
}