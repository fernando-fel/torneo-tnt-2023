package com.example.torneo.Navigation


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.torneo.Pantallas.ForgotPassword
import com.example.torneo.Pantallas.LoginPage
import com.example.torneo.Pantallas.Routes
import com.example.torneo.Pantallas.SesionIncorrecto
import com.example.torneo.Pantallas.SesionOk
import com.example.torneo.Pantallas.SignUp
import com.example.torneo.Splash.SplashScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.SplashScreen.route)
    {
    composable(Routes.SplashScreen.route) {
        SplashScreen(navController)
    }

    composable(Routes.Login.route) {
        LoginPage(navController = navController)
    }
    composable(Routes.SignUp.route) {
        SignUp(navController = navController)
    }
    composable(Routes.ForgotPassword.route) {
        ForgotPassword(navController = navController)
    }
    composable(Routes.SesionIncorrecto.route){
        SesionIncorrecto(navController = navController)
    }
    composable(Routes.SesionOk.route){
        SesionOk(navController = navController)
    }

}

}