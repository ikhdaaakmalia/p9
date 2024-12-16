package com.ikhdaamel.ucp2.repository

import com.ikhdaamel.ucp2.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

interface RepoDosen {
    suspend fun insertDosen(dosen: Dosen)
    fun getAllDosen(): Flow<List<Dosen>>
}