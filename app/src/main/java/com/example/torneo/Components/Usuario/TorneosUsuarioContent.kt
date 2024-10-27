package com.example.torneo.Components.Usuario

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.torneo.Components.TorneoCard
import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.Core.Data.Entity.Torneo
import com.example.torneo.Core.Data.repository.Torneos

@Composable
fun TorneosUsuarioContent (
    padding: PaddingValues,
    torneos : Torneos,
    deleteTorneo: (torneo: Torneo)->Unit,
    navigateToUpdateTorneoScreen: (torneoId: Int)-> Unit,
    navegarParaUnaFecha: (torneoId: Int)-> Unit,
    mostrarTodos : Boolean,
    mostrarFinalizados :  Boolean,
    mostrarEnCurso : Boolean
){
    val searchQueryTodos = remember { mutableStateOf("") }
    val searchQueryFinalizados = remember { mutableStateOf("") }
    val searchQueryEnCurso = remember { mutableStateOf("") }
    var torneosFinalizados = torneos.filter { torneo -> torneo.estado == "finalizado" }
    var torneosEnCurso = torneos.filter { torneo -> torneo.estado == "EN CURSO" }

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
                    TorneoUsuarioCard(
                        torneo = torneo,
                        deleteTorneo = { deleteTorneo(torneo) },
                        navigateToUpdateTorneoScreen = navigateToUpdateTorneoScreen,
                        navegarParaUnaFecha
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
                it.estado == "finalizados" &&
                        it.nombre.contains(searchQueryFinalizados.value, ignoreCase = true)
            }

            LazyColumn {
                items(filteredFinalizados) { torneo ->
                    TorneoUsuarioCard(
                        torneo = torneo,
                        deleteTorneo = { deleteTorneo(torneo) },
                        navigateToUpdateTorneoScreen = navigateToUpdateTorneoScreen,
                        navegarParaUnaFecha
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
                label = { Text("Buscar EN CURSO") },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )
            val filteredEnCurso = torneos.filter {
                it.estado == "EN CURSO" &&
                        it.nombre.contains(searchQueryEnCurso.value, ignoreCase = true)
            }

            LazyColumn {
                items(filteredEnCurso) { torneo ->
                    TorneoUsuarioCard(
                        torneo = torneo,
                        deleteTorneo = { deleteTorneo(torneo) },
                        navigateToUpdateTorneoScreen = navigateToUpdateTorneoScreen,
                        navegarParaUnaFecha
                    )
                }
            }
        }
    }
}

@Composable
fun FechasUsuarioContent (
    padding: PaddingValues,
    fechas : List<Fecha>,
    deleteFecha: (fecha: Fecha)->Unit,
    navigateToUpdateFechaScreen: (fechaId: Int)-> Unit,
    navegarParaPartidos: (fechaId: Int)-> Unit

){

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ){
        items(fechas){ fecha->
            FechaUsuarioCard(
                fecha = fecha,
                deleteFecha={
                    deleteFecha(fecha)
                },
                navigateToUpdateFechaScreen =
                navigateToUpdateFechaScreen,
                navegarParaPartidos
            )
        }
    }
}

