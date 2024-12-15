package com.example.torneo.Components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Partido
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.TorneoViewModel.EquiposViewModel
import com.example.torneo.TorneoViewModel.PersonasViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartidoCard(
    viewModel: EquiposViewModel = hiltViewModel(),
    viewModel2: PersonasViewModel = hiltViewModel(),
    partido: Partido,
    deletePartido: () -> Unit,
    navigateToUpdatePartidoScreen: (partidoId: Int) -> Unit,
    showDialog: MutableState<Boolean> = remember { mutableStateOf(false) }
) {
    val equipos by viewModel.equipos.collectAsState(initial = emptyList())
    val equipoLocal: Equipo? = equipos.firstOrNull { equipo -> equipo.id.toString() == partido.idLocal.toString() }
    val equipoVisitante: Equipo? = equipos.firstOrNull { equipo -> equipo.id.toString() == partido.idVisitante.toString() }

    var juez by remember { mutableStateOf<Persona?>(null) }

    LaunchedEffect(partido.idPersona) {
        val persona = viewModel2.getPersona(id = partido.idPersona.toInt())
        juez = persona
    }

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(),
        onClick = {
            navigateToUpdatePartidoScreen(partido.id)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "${equipoLocal?.nombre ?: "Local"} - ${equipoVisitante?.nombre ?: "Visitante"}",
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Dia: ${partido.dia}  |  Hora: ${partido.hora}")
                Text(text = "Juez: ${juez?.username ?: "juez"}")
                Text(text = "Cancha: ${partido.numCancha}")
                Text(text = "Resultado: ${partido.resultado}")
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { navigateToUpdatePartidoScreen(partido.id) }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar Partido")
            }
            IconButton(onClick = { showDialog.value = true }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar Torneo")
            }
            if (showDialog.value) {
                MostrarDialogoConfirmacionEliminar(
                    onConfirmar = deletePartido,
                    showDialog = showDialog
                )
            }
        }
    }
}
