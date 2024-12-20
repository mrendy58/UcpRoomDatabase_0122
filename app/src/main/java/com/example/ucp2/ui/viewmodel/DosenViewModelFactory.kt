package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ucp2.repository.RepositoryMatkul

// ViewModelFactory untuk DosenViewModel
class DosenViewModelFactory(
    private val repositoryMatkul: RepositoryMatkul
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DosenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DosenViewModel(repositoryMatkul) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
