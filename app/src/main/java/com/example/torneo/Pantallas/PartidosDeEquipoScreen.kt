package com.example.torneo.Components

import Component.CustomTopAppBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SportsScore
import androidx.compose.material.icons.filled.Stadium
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Partido
import com.example.torneo.Pantallas.Usuario.DetallePartido
import com.example.torneo.TorneoViewModel.PartidosViewModel
import com.example.torneo.TorneoViewModel.EquiposViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartidosDeEquipoScreen(
    viewModel: PartidosViewModel = hiltViewModel(),
    equiposViewModel: EquiposViewModel = hiltViewModel(),
    navController: (partidoId: Int) -> Unit,
    equipoId: Int,
    navControllerBack: NavHostController
) {
    val partidos by viewModel.partidos.collectAsState(initial = emptyList())
    val equipos by equiposViewModel.equipos.collectAsState(initial = emptyList())

    val partidosDeEquipo: List<Partido> = partidos.filter { partido -> ((partido.idLocal.toString() == equipoId.toString()) or (partido.idVisitante.toString() == equipoId.toString())) }
    Scaffold(
        topBar = {
            CustomTopAppBar(navControllerBack, "Listado de Partidos", true)
        },
        content = { padding ->
            if (partidosDeEquipo.isEmpty()) {
                EstadoVacio(padding)
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(partidosDeEquipo) { partido ->
                        val equipoLocal = equipos.firstOrNull { it.id.toString() == partido.idLocal.toString() }
                        val equipoVisitante = equipos.firstOrNull { it.id.toString() == partido.idVisitante.toString() }
                        PartidoCard(partido, equipoLocal, equipoVisitante)
                    }
                }
            }
        }
    )
}

@Composable
fun EstadoVacio(padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.SportsScore,
                contentDescription = "No hay partidos",
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "El equipo no tiene partidos ",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun PartidoCard(partido: Partido, equipoLocal: Equipo?, equipoVisitante: Equipo?) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Encabezado con equipos y resultado
            EncabezadoPartido(equipoLocal, equipoVisitante, partido)

            Divider(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.surfaceVariant
            )

            // Detalles del partido
            DetallesPartido(partido)
        }
    }
}

@Composable
fun EncabezadoPartido(
    equipoLocal: Equipo?,
    equipoVisitante: Equipo?,
    partido: Partido
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Equipo Local
        Text(
            text = equipoLocal?.nombre ?: "Equipo Local",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )

        // VS o Resultado
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(
                text = partido.resultado.ifEmpty { "VS" },
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        // Equipo Visitante
        Text(
            text = equipoVisitante?.nombre ?: "Equipo Visitante",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            textAlign = androidx.compose.ui.text.style.TextAlign.End
        )
    }
}

@Composable
fun DetallesPartido(partido: Partido) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DetallePartido(
            icon = Icons.Default.Schedule,
            label = "Horario",
            value = "${partido.dia} - ${partido.hora}"
        )

        DetallePartido(
            icon = Icons.Default.Person,
            label = "Árbitro",
            value = partido.idPersona
        )

        DetallePartido(
            icon = Icons.Default.Stadium,
            label = "Cancha",
            value = partido.numCancha
        )
    }
}
