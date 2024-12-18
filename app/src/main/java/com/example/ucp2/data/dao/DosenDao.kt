package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ucp2.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

@Dao
interface DosenDao {
    @Insert // operasi create
    suspend fun insertDosen(dosen: Dosen)

    //read
    @Query("SELECT * FROM dosen ORDER BY nama ASC")
    fun getALLDosen(): Flow<List<Dosen>>


}