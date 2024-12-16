package com.ikhdaamel.ucp2.repository

import com.ikhdaamel.ucp2.data.dao.MataKuliahDao
import com.ikhdaamel.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

class LocalRepoMataKuliah (private val mataKuliahDao: MataKuliahDao) : RepoMataKuliah {
    override suspend fun insertMataKuliah(mataKuliah: MataKuliah) {
        mataKuliahDao.insertMataKuliah(mataKuliah)
    }

    override fun getAllMataKuliah(): Flow<List<MataKuliah>> {
        return mataKuliahDao.getAllMataKuliah()
    }

    override fun getMataKuliah(kode: String): Flow<MataKuliah> {
        return mataKuliahDao.getMataKuliah(kode)
    }

    override suspend fun deleteMataKuliah(mataKuliah: MataKuliah) {

    }

    override suspend fun updateMataKuliah(mataKuliah: MataKuliah) {

    }
}