@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.ucp2.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.DsnUiState
import com.example.ucp2.ui.viewmodel.InsertDsnViewModel
import com.example.ucp2.ui.viewmodel.InsertMkViewModel
import com.example.ucp2.ui.viewmodel.ListDosenViewModel
import com.example.ucp2.ui.viewmodel.MataKuliahEvent
import com.example.ucp2.ui.viewmodel.MkUiState
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun InsertMkView(
    onBack: () -> Unit,
    onNavigateMataKuliah: () -> Unit,
    modifier: Modifier = Modifier,
    insertMkViewModel: InsertMkViewModel = viewModel(factory = PenyediaViewModel.Factory),
    insertDsnViewModel: InsertDsnViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = insertMkViewModel.uiState
    val dsnUiState = insertDsnViewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                insertMkViewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Mata Kuliah"
            )

            FormMataKuliah(
                uiState = uiState,
                dsnUiState = dsnUiState,
                onNavigateMataKuliah = { },
                onValueChange = { updatedEvent ->
                    insertMkViewModel.updateState(updatedEvent)
                },
                onSave = {
                    insertMkViewModel.saveData()
                    onNavigateMataKuliah()
                }
            )
        }
    }
}

@Composable
fun FormMataKuliah(
    uiState: MkUiState,
    dsnUiState: DsnUiState,
    onNavigateMataKuliah: () -> Unit,
    onValueChange: (MataKuliahEvent) -> Unit,
    onSave: () -> Unit,
    insertDsnViewModel: InsertDsnViewModel = viewModel(factory = PenyediaViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val jenisOptions = listOf("Wajib", "Peminatan")
    val semesterOptions = listOf("Ganjil", "Genap")
    val dosenList = dsnUiState.listDosen.map { it.nama }
    val dsnUiState by insertDsnViewModel.dsnUiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = uiState.mataKuliahEvent.kode,
            onValueChange = { onValueChange(uiState.mataKuliahEvent.copy(kode = it)) },
            label = { Text("Kode Mata Kuliah") },
            isError = uiState.isEntryValid.kode != null,
            placeholder = { Text("Masukkan Kode Mata Kuliah") },
            modifier = Modifier.fillMaxWidth()
        )
        Text(uiState.isEntryValid.kode ?: "", color = Color.Red)

        OutlinedTextField(
            value = uiState.mataKuliahEvent.namaMk,
            onValueChange = { onValueChange(uiState.mataKuliahEvent.copy(namaMk = it)) },
            label = { Text("Nama Mata Kuliah") },
            isError = uiState.isEntryValid.namaMk != null,
            placeholder = { Text("Masukkan Nama Mata Kuliah") },
            modifier = Modifier.fillMaxWidth()
        )
        Text(uiState.isEntryValid.namaMk ?: "", color = Color.Red)

        Text("Jenis Mata Kuliah")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            jenisOptions.forEach { jenis ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    RadioButton(
                        selected = uiState.mataKuliahEvent.jenisMK == jenis,
                        onClick = { onValueChange(uiState.mataKuliahEvent.copy(jenisMK = jenis)) }
                    )
                    Text(jenis)
                }
            }
        }
        Text(uiState.isEntryValid.jenisMK ?: "", color = Color.Red)

        OutlinedTextField(
            value = uiState.mataKuliahEvent.sks,
            onValueChange = { onValueChange(uiState.mataKuliahEvent.copy(sks = it)) },
            label = { Text("SKS") },
            isError = uiState.isEntryValid.sks != null,
            placeholder = { Text("Masukkan SKS") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Text(uiState.isEntryValid.sks ?: "", color = Color.Red)

        Text("Semester")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            semesterOptions.forEach { semester ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    RadioButton(
                        selected = uiState.mataKuliahEvent.semester == semester,
                        onClick = { onValueChange(uiState.mataKuliahEvent.copy(semester = semester)) }
                    )
                    Text(semester)
                }
            }
        }
        Text(uiState.isEntryValid.semester ?: "", color = Color.Red)

        DropdownMenuField(
            label = "Dosen Pengampu",
            options = dosenList,
            selectedOption = uiState.mataKuliahEvent.DosenPengampu,
            onOptionSelected = { selected ->
                onValueChange(uiState.mataKuliahEvent.copy(DosenPengampu = selected))
            },
            isError = uiState.isEntryValid.DosenPengampu != null,
            errorMessage = uiState.isEntryValid.DosenPengampu
        )

        Button(onClick = onSave, modifier = Modifier.fillMaxWidth()) {
            Text("Simpan")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var currentSelection by remember { mutableStateOf(selectedOption) }

    Column {
        OutlinedTextField(
            value = currentSelection,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ArrowDropDown else Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            },
            isError = isError,
            modifier = Modifier.fillMaxWidth()
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        currentSelection = option
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }

        if (isError && errorMessage != null) {
            Text(errorMessage, color = Color.Red)
        }
    }
}
