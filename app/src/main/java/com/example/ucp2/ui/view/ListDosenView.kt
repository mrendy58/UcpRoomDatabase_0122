package com.example.ucp2.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.ui.viewmodel.ListDosenViewModel
import com.example.ucp2.ui.viewmodel.DosenListUiState
import com.example.ucp2.ui.customwidget.TopAppBar
import kotlinx.coroutines.launch

@Composable
fun ListDosenView(
    viewModel: ListDosenViewModel = viewModel(),
    onDosenClick: (String) -> Unit, // You can replace this with any action when a dosen item is clicked
    onAddDosen: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Daftar Dosen",
                showBackButton = false,
                onBack = { },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddDosen,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Dosen")
            }
        }
    ) { innerPadding ->
        val dosenUiState by viewModel.dosenListUiState.collectAsState()

        BodyListDosenView(
            dosenUiState = dosenUiState,
            onClick = onDosenClick,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
@Composable
fun BodyListDosenView(
    dosenUiState: DosenListUiState,
    onClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    when {
        dosenUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        dosenUiState.isError -> {
            LaunchedEffect(dosenUiState.errorMessage) {
                dosenUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }
        dosenUiState.listDosen.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data dosen.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        else -> {
            ListDosen(
                listDosen = dosenUiState.listDosen,
                onClick = onClick,
                modifier = modifier
            )
        }
    }
}

