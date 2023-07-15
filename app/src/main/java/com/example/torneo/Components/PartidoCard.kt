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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Partido
import com.example.torneo.TorneoViewModel.EquiposViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartidoCard(
    viewModel: EquiposViewModel = hiltViewModel(),
    partido: Partido,
    deletePartido: ()-> Unit,
    navigateToUpdatePartidoScreen: (partidoId: Int)-> Unit
){
    val equipos by viewModel.equipos.collectAsState(initial = emptyList() )
    val equipoLocal: Equipo? = equipos.firstOrNull{ equipo -> equipo.id == partido.idLocal }
    val equipoVisitante: Equipo? = equipos.firstOrNull{ equipo -> equipo.id == partido.idVisitante }
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
                Text(text = "Dia: ${partido.dia}  |  Hora: ${partido.hora}")
                Text(text = "Juez: ${partido.idPersona}")
                Text(text = "Cancha: ${partido.numCancha}")
                Text(text = "Resultado: ${partido.resultado}")
            }
            Spacer(
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {navigateToUpdatePartidoScreen(partido.id)}) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar Partido" )
            }
            IconButton(onClick = deletePartido ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar Partido" )
            }

        }
    }
}
