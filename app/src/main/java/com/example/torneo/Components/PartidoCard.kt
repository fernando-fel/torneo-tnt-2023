package com.example.torneo.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartidoCard(
    viewModel: EquiposViewModel = hiltViewModel(),
    viewModel2: PersonasViewModel = hiltViewModel(),
    partido: Partido,
    deletePartido: ()-> Unit,
    navigateToUpdatePartidoScreen: (partidoId: Int)-> Unit,
    showDialog: MutableState<Boolean> = remember { mutableStateOf(false) }
){
    val equipos by viewModel.equipos.collectAsState(initial = emptyList() )
    val equipoLocal: Equipo? = equipos.firstOrNull{ equipo -> equipo.id.toString() == partido.idLocal.toString() }
    val equipoVisitante: Equipo? = equipos.firstOrNull{ equipo -> equipo.id.toString() == partido.idVisitante.toString() }
    val juez by remember { mutableStateOf<Persona?>(null) }
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation() ,
        onClick = {
            navigateToUpdatePartidoScreen(partido.id)
        }

    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column() {
                Text(
                    text = (equipoLocal?.nombre.toString() + "-" + equipoVisitante?.nombre.toString()),
                    fontWeight = FontWeight.Bold
                )
                LaunchedEffect(juez?.nombre) {
                    val juez = viewModel2.getPersona(id=partido.idPersona.toInt())
                }

                Text(text = "Dia: ${partido.dia}  |  Hora: ${partido.hora}")
                Text(text = "Juez: ${juez?.nombre}")
                Text(text = "Cancha: ${partido.numCancha}")
                Text(text = "Resultado: ${partido.resultado}")
            }
            Spacer(
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {navigateToUpdatePartidoScreen(partido.id)}) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar Partido" )
            }
            IconButton(onClick = { showDialog.value = true }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar Torneo" )
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
