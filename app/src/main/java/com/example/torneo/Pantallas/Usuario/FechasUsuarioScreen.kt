package com.example.torneo.Pantallas.Usuario

import Component.CustomTopAppBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.torneo.Components.FechaCard
import com.example.torneo.Components.Usuario.FechaUsuarioCard
import com.example.torneo.Core.Data.Entity.Torneo
import com.example.torneo.TorneoViewModel.FechasViewModel
import com.example.torneo.TorneoViewModel.TorneosViewModel

@Composable
fun FechasUsuarioScreen(
    viewModel: FechasViewModel = hiltViewModel(),
    viewModel2: TorneosViewModel = hiltViewModel(),
    navController: (idFecha: Int) -> Unit,
    torneoId: Int,
    navigateToPartidoScreen: (fechaId: Int) -> Unit,
    navControllerBack: NavHostController
) {
    var torneo by remember { mutableStateOf<Torneo?>(null) }
    var selectedTabIndex by remember { mutableStateOf(0) }

    LaunchedEffect(torneoId) {
        torneo = viewModel2.getTorneo(torneoId)
    }

    val fechas by viewModel.fechas.collectAsState(initial = emptyList())
    val fechasDeTorneo = fechas.filter { it.idTorneo.toString() == torneoId.toString() }
    val fechasProgramadas = fechasDeTorneo.filter { it.estado == "programado" }
    val fechasFinalizadas = fechasDeTorneo.filter { it.estado == "finalizado" }

    Scaffold(
        topBar = {
            CustomTopAppBar(navControllerBack, "Fechas", true)
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                torneo?.let {
                    Text(
                        it.nombre,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                Divider(color = Color.Black, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))

                // Pestañas
                TabRow(selectedTabIndex = selectedTabIndex) {
                    Tab(
                        selected = selectedTabIndex == 0,
                        onClick = { selectedTabIndex = 0 },
                        text = { Text("Próxima Fecha") }
                    )
                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = { selectedTabIndex = 1 },
                        text = { Text("Fechas Anteriores") }
                    )
                }

                // Contenido de las pestañas
                when (selectedTabIndex) {
                    0 -> {
                        Text(text = "Próxima fecha", fontWeight = FontWeight.Bold)
                        if (fechasProgramadas.isNotEmpty()) {
                            fechasProgramadas.forEach { fecha ->
                                FechaUsuarioCard(
                                    fecha = fecha,
                                    deleteFecha = { viewModel.deleteFecha(fecha) },
                                    navigateToUpdateFechaScreen = navController,
                                    navigateToPartidoScreen = navigateToPartidoScreen,
                                )
                                MatchCard(teamA = "EQUIPO A", teamB = "EQUIPO B", field = "CANCHA N1", time = "11:00hs") // Reemplaza con los datos de 'fecha'
                            }
                        } else {
                            Text("No hay fechas programadas", color = Color.Gray)
                        }
                    }
                    1 -> {
                        Text(text = "Fechas anteriores", fontWeight = FontWeight.Bold)
                        if (fechasFinalizadas.isNotEmpty()) {
                            fechasFinalizadas.forEach { fecha ->
                                MatchCard(teamA = "EQUIPO C", teamB = "EQUIPO D", field = "CANCHA N2", time = "12:00hs") // Reemplaza con los datos de 'fecha'
                            }
                        } else {
                            Text("No hay fechas finalizadas", color = Color.Gray)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
}

@Composable
fun MatchCard(teamA: String, teamB: String, field: String, time: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(text = "$teamA vs $teamB", fontWeight = FontWeight.Bold)
        Text(text = field)
        Text(text = "HORA $time")
    }
}
