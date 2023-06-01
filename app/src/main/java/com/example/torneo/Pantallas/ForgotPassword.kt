package com.example.torneo.Pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import Component.CustomTopAppBar

//import Component.CustomTopAppBar


@Composable
@ExperimentalMaterial3Api
fun ForgotPassword(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldWithTopBarForgotPass(navController)
    }
}

@Composable
@ExperimentalMaterial3Api
fun ScaffoldWithTopBarForgotPass(navController: NavHostController) {
    Scaffold(
        topBar = {
            CustomTopAppBar(navController, "Olvidó su contraseña", true)
        }, content = { padding ->
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFFD0BCFF)
            ) {
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Olvidó su contraseña",
                        fontSize = 30.sp,
                        color = Color.Black
                    )
                }
            }

        })
}

