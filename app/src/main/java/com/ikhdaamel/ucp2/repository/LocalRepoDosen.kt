package com.ikhdaamel.ucp2.repository

import com.ikhdaamel.ucp2.data.dao.DosenDao
import com.ikhdaamel.ucp2.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

class LocalRepoDosen (private val dosenDao: DosenDao) : RepoDosen{
    override suspend fun insertDosen(dosen: Dosen) {
        dosenDao.insertDosen(dosen)
    }

    override fun getAllDosen() : Flow<List<Dosen>> {
        return dosenDao.getAllDosen()
    }

    override fun getDosen(nidn: String): Flow<Dosen> {
        return dosenDao.getDosen(nidn)
    }
}