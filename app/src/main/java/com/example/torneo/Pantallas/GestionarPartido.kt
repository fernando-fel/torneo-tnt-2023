package com.example.torneo.Pantallas

import Component.CustomTopAppBar
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.torneo.Core.Data.Entity.Partido
import com.example.torneo.TorneoViewModel.PartidosViewModel
import kotlinx.coroutines.job

enum class ButtonState {
    Iniciar,
    PrimerTiempo,
    Entretiempo,
    SegundoTiempo,
    Fin
}

@Composable
fun GestionarPartido(
    viewModel: PartidosViewModel = hiltViewModel(),
    navControllerBack: NavHostController,
    partidoId: Int
) {
    val partidos by viewModel.partidos.collectAsState(initial = emptyList() )
    val partido = partidos.first() { partido -> partido.id == partidoId }


    Scaffold(
        topBar = {
            CustomTopAppBar(navControllerBack, "Gestion de Partido", true)
        },
        content = { padding ->
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
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
                                text = "${partido.idLocal}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                            )
                            Text(text = golLocal.value.toString(), fontSize = 30.sp)
                            Text(text = golVisitante.value.toString(), fontSize = 30.sp)
                            Text(
                                text = "${partido.idLocal}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
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
                                var partidoUpdate = partido.copy(estado= stateButton.value.toString())
                                //partido.golLocal = golLocal.value
                                //viewModel.updatepartido(partidoUpdate)
                            },
                            //enabled = stateButton.value != ButtonState.Fin,
                        ) {
                            Text(text = stateButton.value.name, fontSize = 25.sp)
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ){
                        if (stateButton.value == ButtonState.PrimerTiempo || stateButton.value == ButtonState.SegundoTiempo) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                Button(
                                    onClick = {
                                        if (golLocal.value > 0) {
                                            golLocal.value -= 1
                                            var partidoUpdate = partido.copy(golLocal= golLocal.value)
                                            //partido.golLocal = golLocal.value
                                            //viewModel.updatepartido(partidoUpdate)
                                        }
                                    },
                                ) {
                                    Text(text = "-", fontSize = 25.sp)
                                }
                                Button(
                                    onClick = {
                                        if (golLocal.value >= 0) {
                                            golLocal.value += 1
                                            var partidoUpdate = partido.copy(golLocal= golLocal.value)
                                            //partido.golLocal = golLocal.value
                                            //viewModel.updatepartido(partidoUpdate)
                                        }
                                    },
                                ) {
                                    Text(text = "+", fontSize = 25.sp)
                                }

                                Button(
                                    onClick = {
                                        if (golVisitante.value >= 0) {
                                            golVisitante.value += 1
                                            var partidoUpdate = partido.copy(golLocal= golVisitante.value)
                                            //partido.golLocal = golLocal.value
                                            //viewModel.updatepartido(partidoUpdate)
                                        }
                                    },
                                ) {
                                    Text(text = "+", fontSize = 25.sp)
                                }
                                Button(
                                    onClick = {
                                        if (golVisitante.value > 0) {
                                            golVisitante.value -= 1
                                            var partidoUpdate = partido.copy(golVisitante= golVisitante.value)
                                            //partido.golLocal = golLocal.value
                                            //viewModel.updatepartido(partidoUpdate)
                                        }
                                    },
                                ) {
                                    Text(text = "-", fontSize = 25.sp)
                                }
                            }
                        } else if (stateButton.value == ButtonState.Fin){
                            navControllerBack
                        }
                    }
                }
            }
        }
    )
}