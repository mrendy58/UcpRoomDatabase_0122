package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.KrsApp

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // Initializer untuk InsertDsnViewModel
        initializer {
            InsertDsnViewModel(
                repositoryDsn = KrsApp().containerApp.repositoryDsn
            )
        }

        // Initializer untuk InsertMkViewModel
        initializer {
            InsertMkViewModel(
                repositoryMatkul = KrsApp().containerApp.repositoryMatkul
            )
        }

        // Initializer untuk HomeOptionsViewModel
        initializer {
            HomeOptionsViewModel(
                repositoryDsn = KrsApp().containerApp.repositoryDsn,
                repositoryMatkul = KrsApp().containerApp.repositoryMatkul
            )
        }

        // Initializer untuk DetailMkViewModel dengan SavedStateHandle
        initializer {
            val savedStateHandle = createSavedStateHandle()
            DetailMkViewModel(
                repositoryMatkul = KrsApp().containerApp.repositoryMatkul,
                savedStateHandle = savedStateHandle
            )
        }

        // Initializer untuk UpdateMkViewModel dengan SavedStateHandle
        initializer {
            val savedStateHandle = createSavedStateHandle()
            UpdateMkViewModel(
                savedStateHandle = savedStateHandle,
                repositoryMatkul = KrsApp().containerApp.repositoryMatkul
            )
        }
    }
}

// Ekstensi untuk mendapatkan instance KrsApp dari CreationExtras
fun CreationExtras.KrsApp(): KrsApp =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp
