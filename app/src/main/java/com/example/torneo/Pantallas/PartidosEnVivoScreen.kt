package com.example.torneo.Pantallas

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.torneo.Core.Data.Dao.PartidoDao
import com.example.torneo.TorneoViewModel.PartidosViewModel
import com.google.common.collect.Iterables.size

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PartidosEnVivoScreen(viewModel: PartidosViewModel) {
    // Obteniendo los partidos de hoy como un estado
    val partidosHoy = viewModel.loadPartidosDeHoy().collectAsState(initial = emptyList()).value
    Log.d("PArtidossss", size(partidosHoy).toString())

    LaunchedEffect(Unit) {
        viewModel.loadPartidosDeHoy()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Partidos en Vivo") },
                navigationIcon = {
                    IconButton(onClick = { /* Acción de navegación */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val partidosAgrupados = agruparPartidos(partidosHoy)

            partidosAgrupados.forEach { torneo ->
                item {
                    Text(torneo.nombreTorneo, style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(8.dp))
                }
                torneo.fechas.forEach { fecha ->
                    item {
                        Text("Fecha ${fecha.numeroFecha}", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(start = 16.dp))
                    }
                    fecha.partidos.forEach { partido ->
                        item {
                            PartidoItem(partido)
                        }
                    }
                }
            }
        }
    }
}

fun agruparPartidos(partidos: List<PartidoDao.PartidoConDetalles>): List<TorneoConPartidos> {
    return partidos.groupBy { it.nombreTorneo }.map { (torneo, partidosPorTorneo) ->
        val fechasAgrupadas = partidosPorTorneo.groupBy { it.numeroFecha }.map { (fecha, partidosPorFecha) ->
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
    val partidos: List<PartidoDao.PartidoConDetalles> // Asegúrate de que esto sea correcto
)

@Composable
fun PartidoItem(partido: PartidoDao.PartidoConDetalles) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "${partido.hora} - ${partido.nombreLocal} vs ${partido.nombreVisitante}",
                style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Estado: ${partido.estado}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Goles: ${partido.golLocal} - ${partido.golVisitante}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

