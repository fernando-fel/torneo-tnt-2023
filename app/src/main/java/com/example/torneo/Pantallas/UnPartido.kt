package com.example.torneo.Pantallas

import Component.CustomTopAppBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun UnPartido(navController: NavHostController){
    val arguments = navController.currentBackStackEntry?.arguments
    val equipoLocal = arguments?.getString("equipoLocal")
    val equipoVisitante = arguments?.getString("equipoVisitante")
    val golLocal = arguments?.getInt("golLocal")
    val golVisitante = arguments?.getInt("golVisitante")
    val partido = Partido(equipoLocal?: "", equipoVisitante?: "", golLocal?:0, golVisitante?:0)

    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldUnPartido(navController, partido)
        //ScaffoldUnPartido(navController)
    }
}

enum class ButtonState {
    Iniciar,
    PrimerTiempo,
    Entretiempo,
    SegundoTiempo,
    Fin
}

@Composable
fun ScaffoldUnPartido(navController: NavHostController, partido: Partido){
    Scaffold(
        topBar = {
            CustomTopAppBar(navController, "", true)
        },
        content = { padding ->
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                val golLocal = remember { mutableStateOf(0) }
                val golVisitante = remember { mutableStateOf(0) }
                val stateButton = remember { mutableStateOf(ButtonState.Iniciar) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 25.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(0.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Text(
                                text = "${partido.equipoLocal}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                            )
                            Text(text = partido.golLocal.toString(), fontSize = 30.sp)
                            Text(text = golVisitante.value.toString(), fontSize = 30.sp)
                            Text(
                                text = "${partido.equipoVisitante}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth().padding(vertical = 5.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Button(
                            onClick = {
                                stateButton.value = when (stateButton.value) {
                                    ButtonState.Iniciar -> ButtonState.PrimerTiempo
                                    ButtonState.PrimerTiempo -> ButtonState.Entretiempo
                                    ButtonState.Entretiempo -> ButtonState.SegundoTiempo
                                    ButtonState.SegundoTiempo -> ButtonState.Fin
                                    ButtonState.Fin -> ButtonState.Fin
                                }
                            },
                            //enabled = stateButton.value != ButtonState.Fin,
                        ) {
                            Text(text = stateButton.value.name, fontSize = 25.sp)
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth().padding(vertical = 5.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ){
                        if (stateButton.value == ButtonState.PrimerTiempo || stateButton.value == ButtonState.SegundoTiempo) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth().padding(vertical = 5.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                Button(
                                    onClick = {
                                        if (partido.golLocal > 0) {
                                            partido.golLocal -= 1
                                        }
                                    },
                                ) {
                                    Text(text = "-", fontSize = 25.sp)
                                }
                                Button(
                                    onClick = {
                                        if (partido.golLocal >= 0) {
                                            partido.golLocal += 1
                                        }
                                    },
                                ) {
                                    Text(text = "+", fontSize = 25.sp)
                                }

                                Button(
                                    onClick = {
                                        if (golVisitante.value >= 0) {
                                            golVisitante.value += 1
                                        }
                                    },
                                ) {
                                    Text(text = "+", fontSize = 25.sp)
                                }
                                Button(
                                    onClick = {
                                        if (golVisitante.value > 0) {
                                            golVisitante.value -= 1
                                        }
                                    },
                                ) {
                                    Text(text = "-", fontSize = 25.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

