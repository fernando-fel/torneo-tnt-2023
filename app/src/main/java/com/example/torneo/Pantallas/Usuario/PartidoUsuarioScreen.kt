package com.example.torneo.Pantallas.Usuario

import Component.CustomTopAppBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.torneo.Components.Usuario.MenuBottomBar
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Partido
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.TorneoViewModel.EquiposViewModel
import com.example.torneo.TorneoViewModel.PartidosViewModel
import com.example.torneo.TorneoViewModel.PersonasViewModel


@Composable
fun PartidoUsuarioScreen(
    viewModel3: PersonasViewModel = hiltViewModel(),
    viewModel: PartidosViewModel = hiltViewModel(),
    viewModel2: EquiposViewModel = hiltViewModel(),
    navController: (partidoId: Int) -> Unit,
    fechaId: Int,
    navControllerBack: NavHostController
) {
    val partidos by viewModel.partidos.collectAsState(initial = emptyList())
    val partidosDeFecha = partidos.filter { it.idFecha == fechaId.toString() }
    val equipos by viewModel2.equipos.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            CustomTopAppBar(navControllerBack, "Programación  de Partidos", true)
        },
        content = { padding ->
            if (partidosDeFecha.isEmpty()) {
                // Estado vacío
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
                            text = "No hay partidos programados",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(partidosDeFecha) { partido ->
                        PartidoCard(viewModel3, partido, equipos)
                    }
                }
            }
        },
        bottomBar = {
            MenuBottomBar(navControllerBack)
        }
    )
}

@Composable
fun PartidoCard(
    viewModel3: PersonasViewModel,
    partido: Partido,
    equipos: List<Equipo>
) {
    val equipoLocal = equipos.firstOrNull { it.id.toString() == partido.idLocal }
    val equipoVisitante = equipos.firstOrNull { it.id.toString() == partido.idVisitante }

    var juez by remember { mutableStateOf<Persona?>(null) }
    LaunchedEffect(partido.idPersona) {
        val persona = viewModel3.getPersona(id = partido.idPersona.toInt())
        juez = persona
    }

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
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
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

            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth(),
                color = MaterialTheme.colorScheme.surfaceVariant
            )

            // Detalles del partido
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Horario
                DetallePartido(
                    icon = Icons.Default.Schedule,
                    label = "Horario",
                    value = "${partido.dia} - ${partido.hora}"
                )

                // Juez
                DetallePartido(
                    icon = Icons.Default.Person,
                    label = "Juez",
                    value = "${juez?.username ?: "juez"}"
                )

                // Cancha
                DetallePartido(
                    icon = Icons.Default.Stadium,
                    label = "Cancha",
                    value = partido.numCancha
                )
            }
        }
    }
}

@Composable
fun DetallePartido(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}