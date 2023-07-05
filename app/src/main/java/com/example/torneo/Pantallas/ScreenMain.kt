package com.example.torneo.Pantallas

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.torneo.Core.Constantes
import com.example.torneo.Core.Constantes.Companion.FECHA_ID
import com.example.torneo.Core.Constantes.Companion.TORNEO_ID
import com.example.torneo.Splash.SplashScreen
import com.example.torneo.TorneoViewModel.PersonasViewModel


@Composable
@ExperimentalMaterial3Api
fun ScreenMain(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen.route
    ){

        composable(Routes.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Routes.Login.route) {
            LoginPage(navController)
        }
        composable(Routes.SignUp.route) {
            val viewModelPersona: PersonasViewModel = viewModel()
            SignUp(viewModelPersona, navController)
        }
        composable(Routes.ForgotPassword.route) {
            ForgotPassword(navController)
        }
        composable(Routes.SesionIncorrecto.route){
            SesionIncorrecto(navController)
        }
        composable(Routes.SesionOk.route){
            SesionOk(navController)
        }
        composable(Routes.TorneosScreen.route){
            TorneosScreen(
                navController = { torneoId ->
                navController.navigate("${Routes.UpdateTorneoScreen.route}/${torneoId}")
            },
                navigateToFechaScreen = { torneoId ->
                    navController.navigate("${Routes.FechasScreen.route}/$torneoId")
                },
            )
        }

        composable("${Routes.UpdateTorneoScreen.route}/{$TORNEO_ID}",
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
        composable(route = Routes.EquiposScreen.route) {
            EquiposScreen(
                navController = { equipoId ->
                    navController.navigate(
                        "${Routes.UpdateEquipoScreen.route}/${equipoId}"
                    )
                }
            )
        }
        composable(
            route = "${Routes.UpdateEquipoScreen.route}/{${Constantes.EQUIPO_ID}}",
            arguments = listOf(
                navArgument("equipoId"){
                    type = NavType.IntType
                }
            )

        ){
                navBackStackEntry ->
            val equipoId = navBackStackEntry.arguments?.getInt(Constantes.EQUIPO_ID) ?:0
            UpdateEquipoScreen(
                equipoId = equipoId,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.Fixture.route){
            Fixture(navController)
        }

        composable(route = "${Routes.FechasScreen.route}/{$TORNEO_ID}",
            arguments = listOf(
                navArgument(TORNEO_ID){
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            val torneoId = navBackStackEntry.arguments?.getInt(TORNEO_ID) ?: 0
            FechasScreen(
                navController = { idFecha ->
                    // Realiza la acción deseada con idFecha
                },
                torneoId = torneoId,
                navigateToPartidoScreen = { fechaId ->
                    navController.navigate("${Routes.PartidoScreen.route}/$fechaId")
                },

            )
        }
        composable(route = "${Routes.PartidoScreen.route}/{$FECHA_ID}",
            arguments = listOf(
                navArgument(FECHA_ID){
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            val fechaId = navBackStackEntry.arguments?.getInt(FECHA_ID) ?: 0
            PartidoScreen(
                navController = { partidoId ->
                    // Realiza la acción deseada con partidoId
                },
                fechaId = fechaId
            )
        }

        composable(
            route = "${Routes.UpdateFechasScreen.route}/{${FECHA_ID}}",
            arguments = listOf(
                navArgument("fechaId"){
                    type = NavType.IntType
                }
            )

        ){
/*                navBackStackEntry ->
            val fechaId = navBackStackEntry.arguments?.getInt(FECHA_ID)?:0
            UpdateFechasScreen(
                equipoId = fechaId,
                navigateBack = {
                    navController.popBackStack()
                }
            )*/
        }

        composable(Routes.Fixture.route){
            Fixture(navController)
        }
        composable(
            route = "${Routes.UnPartido.route}/{equipoLocal}/{equipoVisitante}/{golLocal}/{golVisitante}",
            arguments = listOf(navArgument("equipoLocal") { type = NavType.StringType },
                navArgument("equipoVisitante") { type = NavType.StringType },
                navArgument("golLocal") { type = NavType.IntType },
                navArgument("golVisitante") { type = NavType.IntType })
        ) { backStackEntry ->
            val equipoLocal = backStackEntry.arguments?.getString("equipoLocal")
            val equipoVisitante = backStackEntry.arguments?.getString("equipoVisitante")
            val golLocal = backStackEntry.arguments?.getInt("golLocal")
            val golVisitante = backStackEntry.arguments?.getInt("golVisitante")
            UnPartido(navController)
        }

    }
}