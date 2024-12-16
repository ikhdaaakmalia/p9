package com.ikhdaamel.ucp2.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ikhdaamel.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

interface MataKuliahDao {
    @Query("Select * from MataKuliah order by nama_mk ASC")
    fun getAllMataKuliah(): Flow<List<MataKuliah>>

    @Insert
    suspend fun insertMataKuliah(
        mataKuliah: MataKuliah
    )

    @Update
    suspend fun updateMataKuliah(mataKuliah: MataKuliah)

    @Delete
    suspend fun deleteMataKuliah (mataKuliah: MataKuliah)
}