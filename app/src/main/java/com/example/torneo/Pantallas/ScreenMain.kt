package com.example.torneo.Pantallas

import MenuUsuario
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.torneo.Core.BaseDeDatos.TorneoDB
import com.example.torneo.Core.Constantes
import com.example.torneo.Core.Constantes.Companion.FECHA_ID
import com.example.torneo.Core.Constantes.Companion.PARTIDO_ID
import com.example.torneo.Core.Constantes.Companion.PERSONA_ID
import com.example.torneo.Core.Constantes.Companion.TORNEO_ID
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.Pantallas.Usuario.EquiposUsuarioScreen
import com.example.torneo.Pantallas.Usuario.FechasUsuarioScreen
import com.example.torneo.Pantallas.Usuario.PartidoUsuarioScreen
import com.example.torneo.Pantallas.Usuario.TorneosUsuarioScreen
import com.example.torneo.Splash.SplashScreen
import com.example.torneo.TorneoViewModel.PersonasViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
@ExperimentalMaterial3Api
fun ScreenMain(database: TorneoDB){
    val navController = rememberNavController()

    // Obtener instancia del DAO (carga de roles a mano en la base de datos)
    val persona = database.personaDao()
    LaunchedEffect(Unit) {
        //persona.deleteAll()
        val admin = Persona(idPersona = 1, nombre = "admin", username = "admin", pass = "admin", rol = "admin")
        val juez = Persona(idPersona = 2, nombre = "juez", username = "juez", pass = "juez", rol = "juez")
        if (persona.getPersona(1) == null) {
            withContext(Dispatchers.IO) {
                persona.insertPersona(admin)
            }
        }
        if (persona.getPersona(2) == null) {
            withContext(Dispatchers.IO) {
                persona.insertPersona(juez)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen.route
    ){

        composable(Routes.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Routes.Login.route) {
            //val persona = database.personaDao()
            LoginPage(navController, persona)
        }
        composable(Routes.SignUp.route) {
            SignUp(navController, persona)
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
        composable(Routes.MenuUsuario.route){
            MenuUsuario(navController)
        }
        composable(Routes.Fixture.route){
            Fixture(navController)
        }
        composable(Routes.TorneosScreen.route){
            TorneosScreen(
                navController = { torneoId ->
                    navController.navigate("${Routes.UpdateTorneoScreen.route}/${torneoId}")
                },
                navigateToFechaScreen = { torneoId ->
                    navController.navigate("${Routes.FechasScreen.route}/$torneoId")
                },
                navControllerBack = navController,
            )
        }
        composable(Routes.TorneosUsuarioScreen.route){
            TorneosUsuarioScreen(
                navController = { torneoId ->
                    navController.navigate("${Routes.FechaUsuarioScreen.route}/${torneoId}")
                },
                navigateToFechaScreen = { torneoId ->
                    navController.navigate("${Routes.FechaUsuarioScreen.route}/$torneoId")
                },
                navControllerBack = navController,
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
        composable(Routes.EquiposScreen.route) {
            EquiposScreen(
                navController = { equipoId ->
                    navController.navigate("${Routes.UpdateEquipoScreen.route}/${equipoId}")
                },
                navControllerBack = navController
            )
        }
        composable(Routes.EquipoUsuarioScreen.route) {
            EquiposUsuarioScreen(
                navController = { equipoId ->
                    navController.navigate("${Routes.UpdateEquipoScreen.route}/${equipoId}")
                },
                navControllerBack = navController
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
                navControllerBack = navController
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
                fechaId = fechaId,
                navControllerBack = navController
            )
        }

        composable(route = "${Routes.FechaUsuarioScreen.route}/{$TORNEO_ID}",
            arguments = listOf(
                navArgument(TORNEO_ID){
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            val torneoId = navBackStackEntry.arguments?.getInt(TORNEO_ID) ?: 0
            FechasUsuarioScreen(
                navController = { idFecha ->
                    // Realiza la acción deseada con idFecha
                },
                torneoId = torneoId,
                navigateToPartidoScreen = { fechaId ->
                    navController.navigate("${Routes.PartidoUsuarioScreen.route}/$fechaId")
                },
                navControllerBack = navController
            )
        }
        composable(route = "${Routes.PartidoUsuarioScreen.route}/{$FECHA_ID}",
            arguments = listOf(
                navArgument(FECHA_ID){
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            val fechaId = navBackStackEntry.arguments?.getInt(FECHA_ID) ?: 0
            PartidoUsuarioScreen(
                navController = { partidoId ->
                    // Realiza la acción deseada con partidoId
                },
                fechaId = fechaId,
                navControllerBack = navController
            )
        }


        composable(
            route = "${Routes.UpdateFechasScreen.route}/{${FECHA_ID}}",
            arguments = listOf(
                navArgument("fechaId"){
                    type = NavType.IntType
                }
            )

        ){ navBackStackEntry ->
            val fechaId = navBackStackEntry.arguments?.getInt(FECHA_ID)?:0
            UpdateFechasScreen(
                fechasId = fechaId,
                navigateBack = {
                    navController.popBackStack()
                }
            )
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

        composable(Routes.ListadoDePersonas.route){
            //val persona = getPersonaDao() // Obtén la instancia de tu PersonaDao

            ListadoDePersonas(navController, persona)
        }

        composable(route = "${Routes.PartidosDelJuezScreen.route}/{$PERSONA_ID}",
            arguments = listOf(
                navArgument(PERSONA_ID){
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            val juezId = navBackStackEntry.arguments?.getInt(PERSONA_ID) ?: 0
            PartidosDelJuezScreen(
                navController = { partidoId ->
                    // Realiza la acción deseada con partidoId
                },
                juez = juezId,
                navControllerBack = navController
            )
        }
        composable(route = "${Routes.GestionarPartido.route}/{$PARTIDO_ID}",
            arguments = listOf(
                navArgument(PARTIDO_ID){
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            val partidoId = navBackStackEntry.arguments?.getInt(PARTIDO_ID) ?: 0
            GestionarPartido(
                navControllerBack = navController,
                partidoId = partidoId
            )
        }

    }
}