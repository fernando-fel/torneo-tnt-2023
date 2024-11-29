package com.example.torneo.Pantallas

import Component.CustomTopAppBar
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsScore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.torneo.Core.Data.Entity.Partido
import com.example.torneo.Core.Data.Entity.Torneo
import com.example.torneo.TorneoViewModel.PartidosViewModel
import com.example.torneo.TorneoViewModel.TorneosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PartidosEnVivoScreen(
    viewModel: PartidosViewModel,
    //viewModel2: TorneosViewModel,
    //torneoId: Int,
    navControllerBack: NavHostController,
) {

    /*var torneo by remember { mutableStateOf<Torneo?>(null) }

    LaunchedEffect(torneoId) {
        torneo = viewModel2.getTorneo(torneoId)
    }*/

    viewModel.loadPartidosDeHoyDesdeFirebase()

    // Observamos los partidos obtenidos desde Firebase
    val partidosHoy = viewModel.partidosHoyFirebase

    //val partidosHoy = viewModel.loadPartidosDeHoy().collectAsState(initial = emptyList()).value

    LaunchedEffect(Unit) {
        viewModel.startAutoRefresh()
        //
    // viewModel.loadPartidosDeHoy()
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(navControllerBack, "Partidos en Vivo", true)
            /*TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                title = { Text("Partidos en Vivo") }
            )*/
        }
    ) { paddingValues ->
        if (partidosHoy.isEmpty()) {
            EmptyState()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                val partidosAgrupados = agruparPartidos(partidosHoy)

                partidosAgrupados.forEach { torneo ->
                    item {
                        TorneoHeader(torneo.nombreTorneo)
                    }
                    torneo.fechas.forEach { fecha ->
                        item {
                            FechaHeader(fecha.numeroFecha)
                        }
                        items(
                            items = fecha.partidos,
                            key = { it.id }
                        ) { partidoConDetalles ->
                            PartidoItem(
                                partido = partidoConDetalles, viewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

fun agruparPartidos(partidos: SnapshotStateList<Partido>): List<TorneoConPartidos> {
    return partidos.groupBy { it.idFecha }.map { (torneo, partidosPorTorneo) ->
        val fechasAgrupadas = partidosPorTorneo.groupBy { it.idFecha }.map { (fecha, partidosPorFecha) ->
            Log.d("Fechas", "Torneo: $torneo, Fecha: $fecha, Partidos: ${partidosPorFecha.joinToString { it.id.toString() }}")

            FechaConPartidos(fecha, partidosPorFecha) // Pasar la lista completa de PartidoConDetalles

        }
        TorneoConPartidos(torneo, fechasAgrupadas)
    }
}
data class TorneoConPartidos(
    val nombreTorneo: String,
    val fechas: List<FechaConPartidos>
)

data class FechaConPartidos(
    val numeroFecha: String,
    val partidos: List<Partido> // Asegúrate de que esto sea correcto
)

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Column(
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.SportsScore,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No hay partidos programados para hoy",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun TorneoHeader(nombreTorneo: String) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Torneo $nombreTorneo",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun FechaHeader(numeroFecha: String) {
    Text(
        text = "Fecha $numeroFecha",
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
    )
}

@Composable
fun PartidoItem(partido: Partido, viewModel: PartidosViewModel ) {

    val tiempoActual = viewModel.tiempoActualPartido.collectAsState()

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Hora del partido
            Text(
                text = partido.hora,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Equipos y marcador
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Equipo Local
                Text(
                    text = partido.idLocal,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )

                // Marcador
                Text(
                    text = "${partido.golLocal} - ${partido.golVisitante}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                // Equipo Visitante
                Text(
                    text = partido.idVisitante,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Estado del partido
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = when (partido.estado) {
                        "EN CURSO" -> MaterialTheme.colorScheme.primaryContainer
                        "Finalizado" -> MaterialTheme.colorScheme.secondaryContainer
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    }
                ),
                modifier = Modifier.wrapContentWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = partido.estado,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                    /*if ((partido.estado == "PrimerTiempo") || (partido.estado == "SegundoTiempo")){
                        Text(
                            text = formatTime(tiempoActual.value)
                        )
                    }*/
                }
            }
        }
    }
}