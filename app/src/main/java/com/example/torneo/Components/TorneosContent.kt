package com.example.torneo.Components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    navController : NavHostController

){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ){
        items(torneos){
            torneo->
            TorneoCard(
                torneo = torneo,
                deleteTorneo={
                    deleteTorneo(torneo)
                },
                navigateToUpdateTorneoScreen =
                    navigateToUpdateTorneoScreen,
                navController


            )
        }
    }
}

@Composable
fun FechasContent (
    padding: PaddingValues,
    fechas : List<Fecha>,
    deleteFecha: (fecha: Fecha)->Unit,
    navigateToUpdateFechaScreen: (fechaId: Int)-> Unit

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
                navigateToUpdateFechaScreen
            )
        }
    }
}

