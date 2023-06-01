package com.example.torneo.Pantallas

import Component.CustomTopAppBar
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

@Composable
@ExperimentalMaterial3Api
fun SesionOk(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldWithTopBarSesionOk(navController)
    }
}

@Composable
@ExperimentalMaterial3Api
fun ScaffoldWithTopBarSesionOk(navController: NavHostController) {
    Scaffold(
        topBar = {
            CustomTopAppBar(navController, "Bienvenido!", true)
        },

        content = { padding ->
            Surface(
                modifier = Modifier.fillMaxSize(),
                //color = Color(0xFFD0BCFF)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Bienvenido!", style = TextStyle(fontSize = 60.sp, fontFamily = FontFamily.Cursive))
                    Spacer(modifier = Modifier.height(50.dp))

                    Button(
                        onClick = { navController.navigate(Routes.TorneosScreen.route) },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Torneos",
                            fontSize = 30.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = { /* Acción para la opción Ver partidos */ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Equipos",
                            fontSize = 30.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = { /* Acción para la opción Ver Fechas */ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Jugadores",
                            fontSize = 30.sp
                        )
                    }
                }
            }
        })
}
/*
Column(
modifier = Modifier
.padding(padding)
.fillMaxSize(),
verticalArrangement = Arrangement.Center,
horizontalAlignment = Alignment.CenterHorizontally
) {
    Text(
        text = "Inicio Sesion",
        fontSize = 30.sp,
        color = Color.Black
    )
}*/