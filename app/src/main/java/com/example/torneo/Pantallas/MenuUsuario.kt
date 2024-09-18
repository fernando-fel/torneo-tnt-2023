package com.example.torneo.Pantallas

import Component.CustomTopAppBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.torneo.Mapas.myMarket
import com.example.torneo.Pantallas.HorizontalScroll
import com.example.torneo.Pantallas.Routes

@Composable
@ExperimentalMaterial3Api
fun MenuUsuario(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldWithTopBarMenuUsuario(navController)
    }
}

@Composable
@ExperimentalMaterial3Api
fun ScaffoldWithTopBarMenuUsuario(navController: NavHostController) {
    Scaffold(
        topBar = {
            CustomTopAppBar(navController, "Bienvenido", true)
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Spacer(modifier = Modifier.height(50.dp))
                HorizontalScroll()
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { navController.navigate(Routes.TorneosUsuarioScreen.route) },
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
                    onClick = { navController.navigate(Routes.EquiposUsuarioScreen.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Equipos",
                        fontSize = 30.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { navController.navigate(Routes.PartidosEnVivo.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "En Vivo",
                        fontSize = 30.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                myMarket()

            }
        }
    )
}
