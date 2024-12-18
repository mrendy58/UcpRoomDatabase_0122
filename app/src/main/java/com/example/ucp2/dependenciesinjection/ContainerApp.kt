package com.example.ucp2.dependenciesinjection


import com.example.ucp2.repository.RepositoryDsn
import com.example.ucp2.repository.RepositoryMatkul

interface InterfaceContainerApp{
    val repositoryDsn :RepositoryDsn

    val repositoryMatkul : RepositoryMatkul
}

