package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

@Dao
interface MataKuliahDao {

    @Insert //create
    suspend fun insertMataKuliah(mataKuliah: MataKuliah)
    //read
    @Query("SELECT * FROM matakuliah ORDER BY namaMk ASC")
    fun getALLMataKuliah() : Flow<List<MataKuliah>>
    //detail
    @Query("SELECT * FROM matakuliah WHERE kode = :kode")
    fun getMataKuliah(kode : String): Flow<MataKuliah>
    //delete
    @Delete
    suspend fun deleteMataKuliah (mataKuliah: MataKuliah)
    //update
    @Update
    suspend fun updateMataKuliah (mataKuliah: MataKuliah)
}