package com.example.torneo.Components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.Core.Data.Entity.Torneo
import com.example.torneo.Core.Data.repository.Torneos

@Composable
fun TorneosContent(
    torneos: Torneos,
    deleteTorneo: (torneo: Torneo) -> Unit,
    navigateToUpdateTorneoScreen: (torneoId: Int) -> Unit,
    navegarParaUnaFecha: (torneoId: Int) -> Unit,
    navegarParaUnaInscripcion: (torneoId: Int) -> Unit,
    mostrarTodos: Boolean,
    mostrarFinalizados: Boolean,
    mostrarEnCurso: Boolean
) {
    val searchQueryTodos = remember { mutableStateOf("") }
    val searchQueryFinalizados = remember { mutableStateOf("") }
    val searchQueryEnCurso = remember { mutableStateOf("") }

    if (mostrarTodos) {
        Column(modifier = Modifier.fillMaxSize()) {
            TextField(
                value = searchQueryTodos.value,
                onValueChange = { searchQueryTodos.value = it },
                label = { Text("Buscar Todos") },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )
            val filteredTorneos = torneos.filter {
                it.nombre.contains(searchQueryTodos.value, ignoreCase = true)
            }

            LazyColumn {
                items(filteredTorneos) { torneo ->
                    TorneoCard(
                        torneo = torneo,
                        deleteTorneo = { deleteTorneo(torneo) },
                        navigateToUpdateTorneoScreen = navigateToUpdateTorneoScreen,
                        navegarParaUnaFecha, navegarParaUnaInscripcion
                    )
                }
            }
        }
    }

    if (mostrarFinalizados) {
        Column(modifier = Modifier.fillMaxSize()) {
            TextField(
                value = searchQueryFinalizados.value,
                onValueChange = { searchQueryFinalizados.value = it },
                label = { Text("Buscar Finalizados") },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )
            val filteredFinalizados = torneos.filter {
                it.estado == "Finalizados" &&
                        it.nombre.contains(searchQueryFinalizados.value, ignoreCase = true)
            }

            LazyColumn {
                items(filteredFinalizados) { torneo ->
                    TorneoCard(
                        torneo = torneo,
                        deleteTorneo = { deleteTorneo(torneo) },
                        navigateToUpdateTorneoScreen = navigateToUpdateTorneoScreen,
                        navegarParaUnaFecha, navegarParaUnaInscripcion
                    )
                }
            }
        }
    }

    if (mostrarEnCurso) {
        Column(modifier = Modifier.fillMaxSize()) {
            TextField(
                value = searchQueryEnCurso.value,
                onValueChange = { searchQueryEnCurso.value = it },
                label = { Text("Buscar En Curso") },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )
            val filteredEnCurso = torneos.filter {
                it.estado == "En Curso" &&
                        it.nombre.contains(searchQueryEnCurso.value, ignoreCase = true)
            }

            LazyColumn {
                items(filteredEnCurso) { torneo ->
                    TorneoCard(
                        torneo = torneo,
                        deleteTorneo = { deleteTorneo(torneo) },
                        navigateToUpdateTorneoScreen = navigateToUpdateTorneoScreen,
                        navegarParaUnaFecha, navegarParaUnaInscripcion
                    )
                }
            }
        }
    }
}

@Composable
fun FechasContent(
    padding: PaddingValues,
    fechas: List<Fecha>,
    deleteFecha: (fecha: Fecha) -> Unit,
    navigateToUpdateFechaScreen: (fechaId: Int) -> Unit,
    navegarParaPartidos: (fechaId: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        items(fechas) { fecha ->
            FechaCard(
                fecha = fecha,
                deleteFecha = { deleteFecha(fecha) },
                navigateToUpdateFechaScreen = navigateToUpdateFechaScreen,
                navegarParaPartidos
            )
        }
    }
}
