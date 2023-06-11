package com.example.torneo.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.torneo.Core.Constantes.Companion.TORNEO_ID
import com.example.torneo.Pantallas.Routes
import com.example.torneo.Pantallas.TorneosScreen
import com.example.torneo.Pantallas.UpdateTorneoScreen


@Composable
fun NavGraph (
    navController: NavHostController
){
    NavHost(navController = navController,Routes.TorneosScreen.route){
        composable(
            route = Routes.TorneosScreen.route
        ) {
            TorneosScreen(
                navController = { torneoId ->
                    navController.navigate(
                        "${Routes.UpdateTorneoScreen.route}/${torneoId}"
                    )
                }
            )
        }
            composable(
                route = "${Routes.UpdateTorneoScreen.route}/{$TORNEO_ID}",
                arguments = listOf(
                    navArgument(TORNEO_ID){
                        type = NavType.IntType
                    }
                )

            ){
                navBackStackEntry ->
                val torneoId = navBackStackEntry.arguments?.getInt(TORNEO_ID) ?:0
                UpdateTorneoScreen(
                    torneoId = torneoId,
                    navigateBack ={
                        navController.popBackStack()
                    }
                )
            }
        }
    }