package com.example.torneo.Pantallas.Usuario

import Component.CustomTopAppBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Partido
import com.example.torneo.TorneoViewModel.EquiposViewModel
import com.example.torneo.TorneoViewModel.PartidosViewModel


@Composable
fun PartidoUsuarioScreen(
    viewModel: PartidosViewModel = hiltViewModel(),
    viewModel2: EquiposViewModel = hiltViewModel(),
    navController: (partidoId: Int) -> Unit,
    fechaId: Int,
    navControllerBack: NavHostController
){

    val partidos by viewModel.partidos.collectAsState( initial = emptyList() )
    val partidosDeFecha: List<Partido> = partidos.filter { partido -> partido.idFecha.toString() == fechaId.toString() }


    Scaffold (
        topBar = {
            CustomTopAppBar(navControllerBack, "Partidos", true)
        },
        content = { padding->
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ){
                if (partidosDeFecha.isNotEmpty()) {
                    items(partidosDeFecha) { partido ->
                        Card(
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier
                                .padding(8.dp, 4.dp)
                                .fillMaxWidth(),
                            elevation = CardDefaults.cardElevation()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp))
                                    .padding(16.dp)
                            ) {
                                val equipos by viewModel2.equipos.collectAsState(initial = emptyList() )
                                val equipoLocal: Equipo? = equipos.firstOrNull{ equipo -> equipo.id.toString() == partido.idLocal.toString() }
                                val equipoVisitante: Equipo? = equipos.firstOrNull{ equipo -> equipo.id.toString() == partido.idVisitante.toString() }
                                Text(
                                    text = "${equipoLocal?.nombre} vs ${equipoVisitante?.nombre}",
                                    fontWeight = FontWeight.Bold)
                                Text(text = "Horario : ${partido.hora} - ${partido.dia}")
                                Text(text = "Juez : ${partido.idPersona}")
                                Text(text = "Cancha : ${partido.numCancha}")
                                Text(text = "Resultado : ${partido.resultado}")
                            }
                        }
                    }
                } else {
                    print("borrar")
                }
            }
        }
    )
}

/*PartidosUsuarioContent(
                padding = padding,
                partidos = partidosDeFecha,
                deletePartido={
                        partido->
                    viewModel.deletePartido(partido)
                },
                navigateToUpdatePartidoScreen =  navController
            )*/