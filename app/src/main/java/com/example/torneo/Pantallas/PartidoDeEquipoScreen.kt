package com.example.torneo.Components

import Component.CustomTopAppBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.torneo.Core.Data.Entity.Partido
import com.example.torneo.Pantallas.Routes
import com.example.torneo.TorneoViewModel.PartidosViewModel

@ExperimentalMaterial3Api
@Composable
fun PartidosDeEquipoScreen(
    viewModel: PartidosViewModel = hiltViewModel(),
    navController: (partidoId: Int) -> Unit,
    equipo: Int,
    navControllerBack: NavHostController
) {

    val partidos by viewModel.partidos.collectAsState(initial = emptyList() )
    val partidosDeEquipo: List<Partido> = partidos.filter { partido -> ((partido.idLocal == equipo) or (partido.idVisitante == equipo)) }

    Scaffold(
        topBar = {
            CustomTopAppBar(navControllerBack, "Listado de Partidos: " , true)
        },

        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (partidosDeEquipo.isNotEmpty()){
                    items(partidosDeEquipo) { partido ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp, 4.dp),
                                elevation = CardDefaults.cardElevation(),
                                onClick = {
                                    //navControllerBack.navigate("${Routes.GestionarPartido.route}/${partido.id}")
                                    //nuevoRol.value = persona.rol
                                    //mandar a un  partido para gestionar RESULTADOS
                                }
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Text(
                                        text = "Horario: ${partido.dia} - ${partido.hora}",
                                        style = TextStyle(fontWeight = FontWeight.Bold)
                                    )
                                    Text(text = "Lugar: ${partido.numCancha}")
                                    Text(text = "Resultado: ${partido.resultado}")
                                    Text(text = "Estado: ${partido.estado}")
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