package com.example.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.*

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = AlamatNavigasi.DestinasiHome.route) {

        // Home Options
        composable(route = AlamatNavigasi.DestinasiHome.route) {
            HomeOptionsView(
                onNavigateDosen = {
                    navController.navigate(AlamatNavigasi.DestinasiListDosen.route)
                },
                onNavigateMataKuliah = {
                    navController.navigate(AlamatNavigasi.DestinasiListMk.route)
                },
                modifier = modifier
            )
        }

        // List Dosen
        composable(route = AlamatNavigasi.DestinasiListDosen.route) {
            ListDosenView(
                onBack = { navController.popBackStack() },
                onAddDosen = { navController.navigate(AlamatNavigasi.DestinasiDsnInsert.route) },
                onNavigateDosen = { navController.navigate(AlamatNavigasi.DestinasiListDosen.route) },
                onDosenClick = { nidn -> println("Clicked Dosen with NIDN: $nidn") }, // Berikan implementasi
                modifier = modifier
            )
        }

        // List Mata Kuliah
        composable(route = AlamatNavigasi.DestinasiListMk.route) {
            ListMataKuliahView(
                onBack = { navController.popBackStack() },
                onAddMk = { navController.navigate(AlamatNavigasi.DestinasiMkInsert.route) },
                onDetail = { kode ->
                    navController.navigate("${AlamatNavigasi.DestinasiMkDetail.route}/$kode")
                },
                modifier = modifier
            )
        }

        // Insert Dosen
        composable(route = AlamatNavigasi.DestinasiDsnInsert.route) {
            InsertDsnView(
                onBack = { navController.popBackStack() },
                onNavigateDosen = { navController.popBackStack() },
                modifier = modifier
            )
        }

        // Insert Mata Kuliah
        composable(route = AlamatNavigasi.DestinasiMkInsert.route) {
            InsertMkView(
                onBack = { navController.popBackStack() },
                onNavigateMataKuliah = { navController.popBackStack() },
                modifier = modifier
            )
        }

        // Detail Mata Kuliah dengan parameter kode
        composable(
            route = AlamatNavigasi.DestinasiMkDetail.routesWithArg,
            arguments = listOf(
                navArgument(AlamatNavigasi.DestinasiMkDetail.kode) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val kode = backStackEntry.arguments?.getString(AlamatNavigasi.DestinasiMkDetail.kode)
            kode?.let {
                DetailMkView(
                    onBack = { navController.popBackStack() },
                    onEditClick = { navController.navigate("${AlamatNavigasi.DestinasiUpdate.route}/$kode") },
                    onDeleteClick = { navController.popBackStack() },
                    kode = it,
                    modifier = modifier
                )
            }
        }

        // Update Mata Kuliah dengan parameter kode
        composable(
            route = AlamatNavigasi.DestinasiUpdate.routesWithArg,
            arguments = listOf(
                navArgument(AlamatNavigasi.DestinasiUpdate.kode) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val kode = backStackEntry.arguments?.getString(AlamatNavigasi.DestinasiUpdate.kode)
            kode?.let {
                UpdateMkView(
                    onBack = { navController.popBackStack() },
                    onNavigateMataKuliah = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }
    }
}
