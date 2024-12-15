package com.example.torneo.Pantallas

import Component.CustomTopAppBar
import android.os.Build

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SportsScore
import androidx.compose.material.icons.filled.Stadium
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavHostController

import com.example.torneo.Components.Usuario.MenuBottomBar
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.Core.Data.Entity.Partido
import com.example.torneo.Core.Data.Entity.Torneo
import com.example.torneo.Pantallas.Usuario.DetallePartido
import com.example.torneo.TorneoViewModel.EquiposViewModel
import com.example.torneo.TorneoViewModel.FechasViewModel

import com.example.torneo.TorneoViewModel.PartidosViewModel
import com.example.torneo.TorneoViewModel.TorneosViewModel
import kotlinx.coroutines.flow.first


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PartidosEnVivoScreen(
    viewModel: PartidosViewModel,
    navControllerBack: NavHostController,
    viewModel2: TorneosViewModel = hiltViewModel(),
    viewModel3: FechasViewModel = hiltViewModel(),
    viewModel4 : EquiposViewModel = hiltViewModel()

) {
    // Cargar los partidos desde Firebase
    viewModel.loadPartidosDeHoyDesdeFirebase()

    // Observamos los partidos obtenidos desde Firebase
    val partidosHoy = viewModel.partidosHoyFirebase

    LaunchedEffect(Unit) {
        viewModel.startAutoRefresh()
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(navControllerBack, "Partidos en Vivo", true)
        },
        content = { padding ->
            if (partidosHoy.isEmpty()) {
                EmptyState()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    val partidosAgrupados = agruparPartidos(partidosHoy)

                    partidosAgrupados.forEach { torneo ->
                        item {
                            TorneoHeader(torneo.nombreTorneo, viewModel2)
                        }
                        torneo.fechas.forEach { fecha ->
                            item {
                                FechaHeader(fecha.numeroFecha, viewModel3)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                            items(
                                items = fecha.partidos,
                                key = { it.partido.id }
                            ) { partidoConTiempo ->
                                PartidoItem2(
                                    partidoConTiempo,
                                    viewModel = viewModel,
                                    equipovm = viewModel4
                                )
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            MenuBottomBar(navControllerBack)
        }
    )
}

fun agruparPartidos(partidos: SnapshotStateList<PartidosViewModel.PartidoConTiempo>): List<TorneoConPartidos> {
    return partidos.groupBy { it.partido.idFecha }.map { (torneo, partidosPorTorneo) ->
        val fechasAgrupadas = partidosPorTorneo.groupBy { it.partido.idFecha }.map { (fecha, partidosPorFecha) ->
            FechaConPartidos(fecha, partidosPorFecha.map { it }) // Este es el arreglo de fechas agrupadas
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
    val partidos: List<PartidosViewModel.PartidoConTiempo> // Asegúrate de que esto sea correcto
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
private fun TorneoHeader(nombreTorneo: String, torneoVm : TorneosViewModel) {
    var torneo by remember { mutableStateOf<Torneo?>(null) }

    LaunchedEffect(nombreTorneo) {
        torneo = torneoVm.getTorneo(id = nombreTorneo.toInt())
    }
    Surface(
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Torneo : ${torneo.let { it?.nombre }}" ?: " ",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun FechaHeader(numeroFecha: String, fechavm : FechasViewModel) {

    var fecha by remember { mutableStateOf<Fecha?>(null) }

    LaunchedEffect(numeroFecha) {
        fecha = fechavm.getFecha3(id = numeroFecha.toInt())
    }
    Text(
        text = "Fecha Numero : ${fecha.let { it?.numero }}" ?: " ",
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
    )
}
@Composable
fun PartidoItem2(partidoConTiempo: PartidosViewModel.PartidoConTiempo, viewModel: PartidosViewModel, equipovm: EquiposViewModel) {
    val partido = partidoConTiempo.partido
    var partido2 by remember { mutableStateOf<Partido?>(null) }
    var equipo by remember { mutableStateOf<Equipo?>(null) }

    val tiempoTrascurrido = partidoConTiempo.tiempoTrascurrido?.let { segundos ->
        try {
            val minutos = segundos.toInt() / 60
            "$minutos"
        } catch (e: NumberFormatException) {
            ""
        }
    } ?: ""

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        //elevation = CardDefaults.cardElevation(),
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
            DetallePartido(
                icon = Icons.Default.Stadium,
                label = "Cancha",
                value = partido.numCancha
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Mostrar tiempo transcurrido, si está en curso
            if ((partido.estado == "Primer Tiempo") || (partido.estado == "SegudoTiempo")) {
                Text(
                    text = " El tiempo $tiempoTrascurrido'",
                    style = MaterialTheme.typography.headlineSmall, // Aumentado de titleMedium a headlineSmall
                    color = Color.Green, // Cambiado a color verde
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            }
        var nombreLocal by remember { mutableStateOf("") }
        var nombreVisitante by remember { mutableStateOf("") }

        LaunchedEffect(partido.idLocal, partido.idVisitante) {
            val nombres = equipovm.getNombreEquipos(
                partido.idLocal.toInt(),
                partido.idVisitante.toInt()
            ).first()
            nombreLocal = nombres.first
            nombreVisitante = nombres.second
        }
            // Equipos y marcador
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Equipo Local
                Text(
                    text = nombreLocal,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )

                // Marcador (solo se muestra si el partido no está en curso)

                Text(
                    text = when {
                        partido.estado == "EN CURSO" -> ""
                        partido.estado == "Finalizado" -> "Fin"
                        else -> "${partido.golLocal.toString()} - ${partido.golVisitante.toString()}"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                // Equipo Visitante
                Text(
                    text = nombreVisitante,
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
                ) {
                    Text(
                        text = partido.estado,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 22.dp).fillMaxWidth(),
        thickness = 1.dp,
        color = Color.Black
    )

    //Spacer(modifier = Modifier.height(8.dp))
}
