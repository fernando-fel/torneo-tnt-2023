package com.example.torneo.Components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.torneo.Core.Data.Torneo
import com.example.torneo.Core.Data.repository.Torneos

@Composable
fun TorneosContent (
    padding: PaddingValues,
    torneos : Torneos,
    deleteTorneo: (torneo: Torneo)->Unit,
    navigateToUpdateTorneoScreen: (torneoId: Int)-> Unit

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
                    navigateToUpdateTorneoScreen
            )
        }
    }
}