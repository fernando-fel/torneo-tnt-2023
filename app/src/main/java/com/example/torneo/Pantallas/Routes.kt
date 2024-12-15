package com.example.torneo.Pantallas

sealed class Routes(val route: String) {
    object SplashScreen: Routes("SplashScreen")
    object Login : Routes("Login")
    object SignUp : Routes("SignUp")
    object ForgotPassword : Routes("ForgotPassword")
    object SesionIncorrecto : Routes("SesionIncorrecto")
    object SesionOk : Routes("SesionOK")
    object TorneosScreen : Routes("TorneosScreen")
    object FechasScreen : Routes("FechasScreen")
    object InscripcionScreen : Routes("InscripcionScreen-")
    object UpdateFechasScreen : Routes("UpdateFechasScreen")
    object UpdateTorneoScreen : Routes("UpdateTorneoScreen")
    object UpdatePartidoScreen : Routes("UpdatePartidoScreen")
    object EquiposScreen : Routes("EquiposScreen")
    object UpdateEquipoScreen : Routes("UpdateEquipoScreen")
    object Fixture : Routes("Fixture")
    object UnPartido : Routes("UnPartido")
    object ScreenMain: Routes("ScreenMain")

    object PartidoScreen : Routes("PartidoScreen")
    object PartidosEnVivoScreen : Routes("PartidosEnVivoScreen")

    object ListadoDePersonas : Routes("ListadoDePersonas")
    object PartidosDelJuezScreen : Routes("PartidosDelJuezScreen")
    object GestionarPartido : Routes("GestionarPartido")

    object EquiposUsuarioScreen : Routes("EquiposUsuarioScreen")
    object TorneosUsuarioScreen : Routes("TorneosUsuarioScreen")
    object FechaUsuarioScreen : Routes("FechaUsuarioScreen")

    object PartidoUsuarioScreen : Routes("PartidoUsuarioScreen")
    object PartidosDeEquipoScreen : Routes("PartidosDeEquipoScreen")

    object MenuUsuario : Routes("MenuUsuario")


}