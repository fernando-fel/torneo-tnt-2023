package com.example.torneo.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.Core.Data.Entity.Torneo
import com.example.torneo.Core.Data.repository.Torneos

@Composable
fun TorneosContent (
    padding: PaddingValues,
    torneos : Torneos,
    deleteTorneo: (torneo: Torneo)->Unit,
    navigateToUpdateTorneoScreen: (torneoId: Int)-> Unit,
    navegarParaUnaFecha: (torneoId: Int)-> Unit,
    mostrarTodos : Boolean,
    mostrarFinalizados :  Boolean,
    mostrarEnCurso : Boolean
){

var torneosFinalizados = torneos.filter { torneo -> torneo.estado == "finalizado" }
var torneosEnCurso = torneos.filter { torneo -> torneo.estado == "en Curso" }

Row() {
    if (mostrarTodos) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(torneos) { torneo ->
                TorneoCard(
                    torneo = torneo,
                    deleteTorneo = {
                        deleteTorneo(torneo)
                    },
                    navigateToUpdateTorneoScreen =
                    navigateToUpdateTorneoScreen,
                    navegarParaUnaFecha
                )
            }
        }
    }
    if (mostrarFinalizados) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(torneosFinalizados) { torneo ->
                TorneoCard(
                    torneo = torneo,
                    deleteTorneo = {
                        deleteTorneo(torneo)
                    },
                    navigateToUpdateTorneoScreen =
                    navigateToUpdateTorneoScreen,
                    navegarParaUnaFecha
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))

    if (mostrarEnCurso) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(torneosEnCurso) { torneo ->
                TorneoCard(
                    torneo = torneo,
                    deleteTorneo = {
                        deleteTorneo(torneo)
                    },
                    navigateToUpdateTorneoScreen =
                    navigateToUpdateTorneoScreen,
                    navegarParaUnaFecha

                )
            }
        }
    }
}

}


@Composable
fun FechasContent (
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
        items(fechas){
                fecha->
            FechaCard(
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

