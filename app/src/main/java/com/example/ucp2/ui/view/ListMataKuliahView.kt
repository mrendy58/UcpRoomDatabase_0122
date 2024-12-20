package com.example.ucp2.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.ui.viewmodel.MkListUiState
import kotlinx.coroutines.launch

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
                onClick = onClick,
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