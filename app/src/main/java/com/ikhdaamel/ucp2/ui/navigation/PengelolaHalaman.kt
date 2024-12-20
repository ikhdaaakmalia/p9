package com.ikhdaamel.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.compose.composable
import com.ikhdaamel.ucp2.ui.view.HomeView
import com.ikhdaamel.ucp2.ui.view.dosen.HomeDosenView
import com.ikhdaamel.ucp2.ui.view.dosen.InsertDosenView
import com.ikhdaamel.ucp2.ui.view.matakuliah.DetailMatKulView
import com.ikhdaamel.ucp2.ui.view.matakuliah.InsertMatKulView
import com.ikhdaamel.ucp2.ui.view.matakuliah.UpdateMatKulView



@Composable
fun PengelolaHalaman (
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route){
        //home
        composable(
            route = DestinasiHome.route
        ){
            HomeView(
                onNavigateToDosen = {
                    navController.navigate(DestinasiHomeDosen.route)
                },
                onNavigateToMatkul = {
                    navController.navigate(DestinasiInsertMataKuliah.route)
                },
                modifier = modifier
            )
        }

        //home dosen
        composable(
            route = DestinasiHomeDosen.route
        ){
            HomeDosenView(onDetailClick = {nidn ->
                navController.navigate("${DestinasiHomeDosen.route}/$nidn")
                println("Pengelola Halaman: nim = $nidn")
            },
                onAddDsn = {
                    navController.navigate(DestinasiHome.route)
                },
                modifier = modifier
            )
        }

        //insert dosen
        composable(
            route = DestinasiInsertDosen.route
        ){
            InsertDosenView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        //insert matkul
        composable(
            route = DestinasiInsertMataKuliah.route
        ){
            InsertMatKulView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        //update matkul
        composable(
            route = DestinasiUpdate.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdate.KODE){
                    type = NavType.StringType
                }
            )
        ){
            val kode = it.arguments?.getString(DestinasiUpdate.KODE)
            kode?.let { kodeValue ->
            UpdateMatKulView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }

        //detail matkul
        composable(
            route = DestinasiDetailMatKul.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailMatKul.KODE){
                    type = NavType.StringType
                }
            )
        ){
            val kode = it.arguments?.getString(DestinasiDetailMatKul.KODE)
            kode?.let { kode ->
                DetailMatKulView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdate.route}/$it")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}}

