package com.example.ucp2.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2.ui.customwidget.TopAppBar

@Composable
fun HomeOptionsView(
    onNavigateDosen: () -> Unit,
    onNavigateMataKuliah: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "PILIHAN MENU",
                showBackButton = false,
                onBack = { }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MenuButton(
                title = "Daftar Dosen",
                onClick = onNavigateDosen,
                modifier = Modifier.fillMaxWidth()
            )
            MenuButton(
                title = "Daftar Mata Kuliah",
                onClick = onNavigateMataKuliah,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun MenuButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(7.dp)
            .wrapContentHeight()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 20.sp
        )
    }
}
