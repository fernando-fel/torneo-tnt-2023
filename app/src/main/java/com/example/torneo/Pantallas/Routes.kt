package com.example.torneo.Pantallas

import com.example.torneo.Core.Constantes.Companion.TORNEOS_SCREEN
import com.example.torneo.Core.Constantes.Companion.UPDATE_TORNEO_SCREEN

sealed class Routes(val route: String) {
    //object TorneosScreen: Routes(TORNEOS_SCREEN)
    //object UpdateTorneoScreen : Routes(UPDATE_TORNEO_SCREEN)

    //este anda bien

    object Login : Routes("Login")
    object SignUp : Routes("SignUp")
    object ForgotPassword : Routes("ForgotPassword")
    object SesionIncorrecto : Routes("SesionIncorrecto")
    object SesionOk : Routes("SesionOK")
    object TorneosScreen : Routes("TorneosScreen")
    object UpdateTorneoScreen : Routes("UpdateTorneoScreen")

    object EquiposScreen : Routes("EquiposScreen")
    object UpdateEquipoScreen : Routes("UpdateEquipoScreen")


    object SplashScreen: Routes("SplashScreen")

    object ScreenMain: Routes("ScreenMain")

}