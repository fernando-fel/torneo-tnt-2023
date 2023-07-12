package com.example.torneo.Pantallas

import Component.CustomTopAppBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.torneo.Core.Data.Dao.PersonaDao
import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.Core.Data.Entity.Partido
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.Core.Data.repository.PersonaRepository
import com.example.torneo.TorneoViewModel.PartidosViewModel
import com.example.torneo.TorneoViewModel.PersonasViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@ExperimentalMaterial3Api
@Composable
fun PartidosDelJuezScreen(
    viewModel: PartidosViewModel = hiltViewModel(),
    navController: (partidoId: Int) -> Unit,
    juez: Int,
    navControllerBack: NavHostController
) {

    val partidos by viewModel.partidos.collectAsState(initial = emptyList() )
    val partidosDelJuez: List<Partido> = partidos.filter { partido -> partido.idPersona.toInt() == juez }

    Scaffold(
        topBar = {
            CustomTopAppBar(navControllerBack, "Listado de Partidos: " + juez, true)
        },

        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (partidosDelJuez.isNotEmpty()){
                    items(partidosDelJuez) { partido ->
                        if (partido.estado == "Programado") {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp, 4.dp),
                                elevation = CardDefaults.cardElevation(),
                                onClick = {
                                    navControllerBack.navigate("${Routes.GestionarPartido.route}/${partido.id}")
                                    //nuevoRol.value = persona.rol
                                    //mandar a un  partido para gestionar RESULTADOS
                                }
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Text(
                                        text = "PARTIDO: ${partido.idLocal} - ${partido.idVisitante}",
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(text = "Dia: ${partido.dia}  |  Hora: ${partido.hora}")
                                    Text(text = "Lugar: ${partido.numCancha}")
                                    Text(text = "Estado: ${partido.estado}")
                                }
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





