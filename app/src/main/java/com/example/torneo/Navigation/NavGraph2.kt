package com.example.torneo.Navigation

import com.example.torneo.Core.Constantes.Companion.EQUIPO_ID
import com.example.torneo.Pantallas.EquiposScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.torneo.Pantallas.Routes
import com.example.torneo.Pantallas.UpdateEquipoScreen


@Composable
fun NavGraph2 (
    navController: NavHostController
){
    NavHost(navController = navController,Routes.EquiposScreen.route){
        composable(
            route = Routes.EquiposScreen.route
        ) {
            EquiposScreen(
                navController = { equipoId ->
                    navController.navigate(
                        "${Routes.UpdateEquipoScreen.route}/${equipoId}"
                    )
                }
            )
        }
        composable(
            route = "${Routes.UpdateEquipoScreen.route}/{$EQUIPO_ID}",
            arguments = listOf(
                navArgument("equipoId"){
                    type = NavType.IntType
                }
            )

        ){
                navBackStackEntry ->
            val equipoId = navBackStackEntry.arguments?.getInt(EQUIPO_ID) ?:0
            UpdateEquipoScreen(
                equipoId = equipoId,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}