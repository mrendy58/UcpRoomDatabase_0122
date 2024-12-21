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
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.ui.viewmodel.ListMataKuliahViewModel
import com.example.ucp2.ui.viewmodel.MkListUiState
import com.example.ucp2.ui.customwidget.TopAppBar
import kotlinx.coroutines.launch

@Composable
fun ListMataKuliahView(
    viewModel: ListMataKuliahViewModel = viewModel(),
    onDetail: (String) -> Unit,
    onNavigateMataKuliah: () -> Unit,
    onBack: () -> Unit,
    onAddMk: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Daftar Mata Kuliah",
                showBackButton = true,
                onBack = onBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddMk,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Mata Kuliah")
            }
        }
    ) { innerPadding ->
        val mkUiState by viewModel.mkListUiState.collectAsState()

        BodyListMataKuliahView(
            mkUiState = mkUiState,
            onClick = {kode ->
                onDetail(kode)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyListMataKuliahView(
    mkUiState: MkListUiState,
    onClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    when {
        mkUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        mkUiState.isError -> {
            LaunchedEffect(mkUiState.errorMessage) {
                mkUiState.errorMessage.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }
        mkUiState.listMk.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data mata kuliah.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        else -> {
            ListMataKuliah(
                listMk = mkUiState.listMk,
                onClick = {
                    onClick(it)
                    println(it) },
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListMataKuliah(
    listMk: List<MataKuliah>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items = listMk) { mataKuliah ->
            CardMataKuliah(
                mataKuliah = mataKuliah,
                onClick = { onClick(mataKuliah.kode) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardMataKuliah(
    mataKuliah: MataKuliah,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = mataKuliah.namaMk,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "Kode: ${mataKuliah.kode}",
                fontSize = 16.sp
            )
            Text(
                text = "SKS: ${mataKuliah.sks}",
                fontSize = 16.sp
            )
            Text(
                text = "Semester: ${mataKuliah.semester}",
                fontSize = 16.sp
            )
            Text(
                text = "Jenis MK: ${mataKuliah.jenisMK}",
                fontSize = 16.sp
            )
            Text(
                text = "Dosen Pengampu: ${mataKuliah.DosenPengampu}",
                fontSize = 16.sp
            )
        }
    }
}