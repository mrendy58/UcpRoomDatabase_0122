package com.example.ucp2.repository

import com.example.ucp2.data.dao.MataKuliahDao
import com.example.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

class LocalRepositoryMatkul(
    private val mataKuliahDao: MataKuliahDao
) : RepositoryMatkul {
    override suspend fun insertMataKuliah(mataKuliah: MataKuliah){
        mataKuliahDao.insertMataKuliah(mataKuliah)
    }

    override fun getALLMataKuliah(): Flow<List<MataKuliah>>{
        return mataKuliahDao.getALLMataKuliah()
    }

    override fun getMataKuliah(kode : String):Flow<MataKuliah>{
        return mataKuliahDao.getMataKuliah(kode)
    }

    override suspend fun deleteMataKuliah(mataKuliah: MataKuliah){
        mataKuliahDao.deleteMataKuliah(mataKuliah)
    }

    override suspend fun updateMataKuliah(mataKuliah: MataKuliah){
        mataKuliahDao.updateMataKuliah(mataKuliah)
    }
}