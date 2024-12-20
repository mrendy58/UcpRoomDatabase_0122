package com.example.ucp2.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.ui.viewmodel.DetailMkViewModel
import com.example.ucp2.ui.viewmodel.DetailMkUiState
import com.example.ucp2.ui.viewmodel.toMataKuliahEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMkView(
    modifier: Modifier = Modifier,
    viewModel: DetailMkViewModel = viewModel(),
    onBack: () -> Unit = {},
    onEditClick: (String) -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    val detailUiState by viewModel.detailMkUiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Mata Kuliah") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (!detailUiState.isUiEventEmpty) {
                        detailUiState.detailMkUiEvent.kode?.let(onEditClick)
                    }
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Mata Kuliah"
                )
            }
        }
    ) { innerPadding ->
        BodyDetailMk(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState,
            onDeleteClick = {
                viewModel.deleteMataKuliah()
                onDeleteClick()
            }
        )
    }
}

@Composable
fun BodyDetailMk(
    modifier: Modifier = Modifier,
    detailUiState: DetailMkUiState,
    onDeleteClick: () -> Unit = {}
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    when {
        detailUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        detailUiState.isUiEventNotEmpty -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailMk(
                    mataKuliah = detailUiState.detailMkUiEvent.toMataKuliahEntity(),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = { deleteConfirmationRequired = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Delete")
                }
            }
        }

        else -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data Tidak Ditemukan",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

    if (deleteConfirmationRequired) {
        DeleteConfDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteClick()
            },
            onDeleteCancel = {
                deleteConfirmationRequired = false
            }
        )
    }
}

@Composable
fun ItemDetailMk(
    modifier: Modifier = Modifier,
    mataKuliah: MataKuliah
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailMk(judul = "Kode MK", data = mataKuliah.kode)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMk(judul = "Nama MK", data = mataKuliah.namaMk)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMk(judul = "SKS", data = mataKuliah.sks)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMk(judul = "Semester", data = mataKuliah.semester)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMk(judul = "Jenis MK", data = mataKuliah.jenisMK)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailMk(judul = "Dosen Pengampu", data = mataKuliah.DosenPengampu)
        }
    }
}

@Composable
fun ComponentDetailMk(
    modifier: Modifier = Modifier,
    judul: String,
    data: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = data,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DeleteConfDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Hapus Data") },
        text = { Text("Apakah anda yakin ingin menghapus data ini?") },
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Batal")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Ya")
            }
        }
    )
}