package com.example.ucp2.ui.viewmodel

data class DosenListUiState(
    val listDosen: List<String> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)
