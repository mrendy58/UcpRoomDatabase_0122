package com.example.ucp2.dependenciesinjection

import android.content.Context
import com.example.ucp2.data.database.KrsDatabase
import com.example.ucp2.repository.LocalRepositoryDsn
import com.example.ucp2.repository.LocalRepositoryMatkul
import com.example.ucp2.repository.RepositoryDsn
import com.example.ucp2.repository.RepositoryMatkul

interface InterfaceContainerApp{
    val repositoryDsn :RepositoryDsn

    val repositoryMatkul : RepositoryMatkul
}

class ContainerApp(private val context: Context): InterfaceContainerApp{
    override val repositoryDsn:RepositoryDsn by lazy {
        LocalRepositoryDsn(KrsDatabase.getDatabase(context).dosenDao())
    }

    override val repositoryMatkul:RepositoryMatkul by lazy {
        LocalRepositoryMatkul(KrsDatabase.getDatabase(context).mataKuliahDao())
    }
}
