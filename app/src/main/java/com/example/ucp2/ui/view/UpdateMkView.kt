@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.ucp2.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.*
import kotlinx.coroutines.launch

@Composable
fun UpdateMkView(
    onBack: () -> Unit,
    onNavigateMataKuliah: () -> Unit,
    modifier: Modifier = Modifier,
    updateMkViewModel: UpdateMkViewModel = viewModel(factory = PenyediaViewModel.Factory),
    insertDsnViewModel: InsertDsnViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = updateMkViewModel.updateUiState
    val dsnUiState by insertDsnViewModel.dsnUiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val dosenList = dsnUiState.listDosen.map { it.nama }

    var selectedDosen by remember { mutableStateOf("") }

    // Menentukan dosen default dari daftar dosen yang tersedia
    LaunchedEffect(dsnUiState.listDosen) {
        if (dsnUiState.listDosen.isNotEmpty()) {
            selectedDosen = dsnUiState.listDosen.first().nama
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Edit Mata Kuliah"
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Form untuk Update Mata Kuliah
            UpdateBodyMk(
                uiState = uiState,
                dosenList = dosenList,
                onValueChange = { updatedEvent ->
                    updateMkViewModel.updateState(updatedEvent) // Update form data di ViewModel
                },
                onClick = {
                    coroutineScope.launch {
                        if (updateMkViewModel.validateFieldsMk()) {
                            updateMkViewModel.updateData(selectedDosen)
                            snackbarHostState.showSnackbar("Data berhasil diupdate!")
                            onNavigateMataKuliah()
                        } else {
                            snackbarHostState.showSnackbar("Validasi gagal. Periksa input Anda.")
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun UpdateBodyMk(
    modifier: Modifier = Modifier,
    uiState: MkUiState,
    dosenList: List<String>,
    onValueChange: (MataKuliahEvent) -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMataKuliah(
            uiState = uiState,
            dosenList = dosenList,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update")
        }
    }
}

@Composable
fun FormMataKuliah(
    uiState: MkUiState,
    dosenList: List<String>,
    onValueChange: (MataKuliahEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Input Nama Mata Kuliah
        OutlinedTextField(
            value = uiState.mataKuliahEvent.namaMk,
            onValueChange = { newName ->
                onValueChange(
                    uiState.mataKuliahEvent.copy(namaMk = newName)
                )
            },
            label = { Text("Nama Mata Kuliah") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))


        var expanded by remember { mutableStateOf(false) }
        var selectedDosen by remember { mutableStateOf(uiState.mataKuliahEvent.DosenPengampu) }

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedDosen,
                onValueChange = {},
                label = { Text("Dosen Pengampu") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                dosenList.forEach { dosen ->
                    DropdownMenuItem(
                        text = { Text(dosen) },
                        onClick = {
                            selectedDosen = dosen
                            onValueChange(
                                uiState.mataKuliahEvent.copy(DosenPengampu = dosen)
                            )
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))


        OutlinedTextField(
            value = uiState.mataKuliahEvent.sks.toString(),
            onValueChange = { sks ->
                onValueChange(
                    uiState.mataKuliahEvent.copy(sks)
                )
            },
            label = { Text("SKS") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
