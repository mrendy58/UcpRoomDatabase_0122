package com.example.ucp2.repository

import com.example.ucp2.data.dao.DosenDao

abstract class RepositoryMatkulImpl(
    private val dosenDao: DosenDao
) : RepositoryMatkul {

    override suspend fun getDosenList(): List<String> {
        return dosenDao.getDosenList()
    }
}

