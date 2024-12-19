package com.example.ucp2.repository
import com.example.ucp2.data.dao.DosenDao
import com.example.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryMatkul {
    suspend fun insertMataKuliah(mataKuliah: MataKuliah)

    fun getALLMataKuliah(): Flow<List<MataKuliah>>

    fun getMataKuliah(kode: String): Flow<MataKuliah>

    suspend fun deleteMataKuliah(mataKuliah: MataKuliah)

    suspend fun updateMataKuliah(mataKuliah: MataKuliah)

    suspend fun getDosenList(dosenDao: DosenDao): List<String> {
        return dosenDao.getAllDosen() // Panggil method dari instance dosenDao
    }
}