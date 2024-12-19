package com.example.ucp2.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ucp2.ui.viewmodel.HomeOptionsViewModel
import com.example.ucp2.ui.customwidget.TopAppBar
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeOptionsView(
    navController: NavController,
    modifier: Modifier = Modifier,
    homeOptionsViewModel: HomeOptionsViewModel = viewModel()
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
                    onClick = {
                        // Update state
                        homeOptionsViewModel.updateMenuSelection("Dosen")
                        navController.navigate("listDosen")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Dosen")
                }
                Spacer(modifier = Modifier.height(16.dp))
                // Tombol untuk Mata Kuliah
                Button(
                    onClick = {
                        // Update state
                        homeOptionsViewModel.updateMenuSelection("Mata Kuliah")
                        navController.navigate("listMataKuliah")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Mata Kuliah")
                }
            }
        }
    )
}
