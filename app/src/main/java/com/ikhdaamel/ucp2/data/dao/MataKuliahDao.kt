package com.ikhdaamel.ucp2.data.dao

import androidx.room.Query
import com.ikhdaamel.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

interface MataKuliahDao {
    @Query("Select * from MataKuliah order by nama_mk ASC")
    fun getAllMataKuliah(): Flow<List<MataKuliah>>
}