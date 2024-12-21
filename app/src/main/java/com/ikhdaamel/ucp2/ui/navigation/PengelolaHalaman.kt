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
import com.ikhdaamel.ucp2.ui.view.InsertMatKulView
import com.ikhdaamel.ucp2.ui.view.UpdateMatKulView



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
                    navController.navigate(DestinasiHomeMatKul.route)
                },
                modifier = modifier
            )
        }

//        //home dosen
//        composable(
//            route = DestinasiHomeDosen.route
//        ){
//            HomeDosenView(onDetailClick = {nidn ->
//                navController.navigate("${DestinasiHomeDosen.route}/$nidn")
//                println("Pengelola Halaman: nidn = $nidn")
//            },
//                onAddDsn = {
//                    navController.navigate(DestinasiHome.route)
//                },
//                modifier = modifier
//            )
//        }
//
//        //home matkul
//        composable(
//            route = DestinasiHomeMatKul.route
//        ){
//            HomeMatKulView(onDetailClick = {kode ->
//                navController.navigate("${DestinasiHomeMatKul.route}/$kode")
//                println("Pengelola Halaman: kode = $kode")
//            },
//                onAddMatKul = {
//                    navController.navigate(DestinasiHome.route)
//                },
//                modifier = modifier
//            )
//        }

        //insert dosen
        composable(
            route = DestinasiInsertDosen.route
        ){
            InsertDosenView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigateToDosen = {
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
                onNavigateInMatkul = {
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
                onNavigateUpMatkul = {
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

