package com.ikhdaamel.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.compose.composable
import com.ikhdaamel.ucp2.ui.view.HomeView
import com.ikhdaamel.ucp2.ui.view.InsertDosenView
import com.ikhdaamel.ucp2.ui.view.DetailMatKulView
import com.ikhdaamel.ucp2.ui.view.HomeDosenView
import com.ikhdaamel.ucp2.ui.view.HomeMatKulView
import com.ikhdaamel.ucp2.ui.view.InsertMatKulView
import com.ikhdaamel.ucp2.ui.view.UpdateMatKulView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route
    ) {
        // Home
        composable(route = DestinasiHome.route) {
            HomeView(
                onNavigateDosen = { navController.navigate(DestinasiHomeDosen.route) },
                onNavigateMatkul = { navController.navigate(DestinasiHomeMatKul.route) },
                modifier = modifier
            )
        }

        //home dosen
        composable(route = DestinasiHomeDosen.route) {
            HomeDosenView(
                onBack = {
                    navController.popBackStack()
                },
                onAddDsn = {
                    navController.navigate(DestinasiInsertDosen.route)
                },
                onNavigateDosen = {
                    navController.navigate(DestinasiHomeDosen.route)
                },
                modifier = modifier
            )
        }

        // Insert Dosen
        composable(route = DestinasiInsertDosen.route) {
            InsertDosenView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }

        //home matkul
        composable(route = DestinasiHomeMatKul.route) {
            HomeMatKulView(
                onBack = {
                    navController.popBackStack()
                },
                onAddMatKul = {
                    navController.navigate(DestinasiInsertMataKuliah.route)
                },
                onNavigateHome = {
                    navController.navigate(DestinasiHome.route)
                },
                modifier = modifier
            )
        }

        // Insert Mata Kuliah
        composable(route = DestinasiInsertMataKuliah.route) {
            InsertMatKulView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }

        // Update Mata Kuliah
        composable(
            route = DestinasiUpdate.routesWithArg,
            arguments = listOf(navArgument(DestinasiUpdate.KODE) { type = NavType.StringType })
        ) {
            val kode = it.arguments?.getString(DestinasiUpdate.KODE)
            kode?.let { kode ->
                UpdateMatKulView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }

        // Detail Mata Kuliah
        composable(
            route = DestinasiDetailMatKul.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetailMatKul.KODE) { type = NavType.StringType })
        ) {
            val kode = it.arguments?.getString(DestinasiDetailMatKul.KODE)
            kode?.let { kodeValue ->
                DetailMatKulView(
                    onBack = { navController.popBackStack() },
                    onEditClick = { navController.navigate("${DestinasiUpdate.route}/$kodeValue") },
                    onDeleteClick = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }
    }
}

