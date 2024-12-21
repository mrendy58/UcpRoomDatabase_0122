package com.example.ucp2.ui.view

import android.graphics.Paint.Align
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.navigation.AlamatNavigasi
import com.example.ucp2.ui.viewmodel.DosenEvent
import com.example.ucp2.ui.viewmodel.DsnUiState
import com.example.ucp2.ui.viewmodel.FormErrorState
import com.example.ucp2.ui.viewmodel.InsertDsnViewModel
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@Composable
fun InsertDsnView(
    onBack: () -> Unit,
    onNavigateDosen: () -> Unit,
    onAddDosen: () -> Unit = { },
    modifier: Modifier = Modifier,
    viewModel: InsertDsnViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutinScope = rememberCoroutineScope()

    // Observe Snackbar message changes
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutinScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage() // Reset Snackbar message after showing it
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
            // top app bar tambah dosen
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Dosen"
            )

            // Form untuk insert Dosen
            InsertBodyDsn(
                uiState = uiState,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent) // Update form data di ViewModel
                },
                onClick = {
                    viewModel.saveData() // Simpan data
                    onNavigateDosen()
                }
            )
        }
    }
}

@Composable
fun InsertBodyDsn(
    modifier: Modifier = Modifier,
    uiState: DsnUiState,
    onValueChange: (DosenEvent) -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormDosen(
            dosenEvent = uiState.dosenEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}
object DestinasiInsert : AlamatNavigasi{
    override val route: String = "insertdosen"
}

@Composable
fun FormDosen(
    dosenEvent: DosenEvent = DosenEvent(),
    onValueChange: (DosenEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(20.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dosenEvent.nidn,
            onValueChange = { onValueChange(dosenEvent.copy(nidn = it)) },
            label = { Text("NIDN") },
            isError = errorState.nidn != null,
            placeholder = { Text("Masukkan NIDN") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.nidn ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dosenEvent.nama,
            onValueChange = { onValueChange(dosenEvent.copy(nama = it)) },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan Nama") }
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mengatur "Jenis Kelamin" dan RadioButton untuk rata tengah
        Text(
            text = "Jenis Kelamin",
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        // Menambahkan Row dengan radio button terpusat
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center, // Menjadikan radio button terpusat
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf("Laki-laki", "Perempuan").forEach { jk ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = dosenEvent.jenisKelamin == jk,
                        onClick = { onValueChange(dosenEvent.copy(jenisKelamin = jk)) }
                    )
                    Text(text = jk)
                }
            }
        }

        Text(
            text = errorState.jenisKelamin ?: "",
            color = Color.Red
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewFormDosen() {
    val dosenEvent = DosenEvent(nidn = "", nama = "", jenisKelamin = "")
    val errorState = FormErrorState(nidn = null, nama = null, jenisKelamin = null)

    FormDosen(
        dosenEvent = dosenEvent,
        onValueChange = {},
        errorState = errorState
    )
}
