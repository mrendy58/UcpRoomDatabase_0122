package com.example.ucp2.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ucp2.ui.customwidget.TopAppBar



@Composable
fun HomeOptionsView(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                onBack = { },
                showBackButton = false,
                judul = "PILIHAN MENU"
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Tombol untuk Dosen
                Button(
                    onClick = { navController.navigate("listDosen") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Dosen")
                }
                Spacer(modifier = Modifier.height(16.dp))
                // Tombol untuk Mata Kuliah
                Button(
                    onClick = { navController.navigate("listMataKuliah") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Mata Kuliah")
                }
            }
        }
    )
}
