package com.example.torneo.Components

import com.example.torneo.Core.Data.repository.Equipos
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.torneo.Core.Data.Entity.Equipo

@Composable
fun EquipoContent (
    padding: PaddingValues,
    equipos : Equipos,
    deleteEquipo: (equipo: Equipo)->Unit,
    navigateToUpdateEquipoScreen: (equipoId: Int)-> Unit

){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ){
        items(equipos){
                equipo->
            EquipoCard(
                equipo = equipo,
                deleteEquipo={
                    deleteEquipo(equipo)
                },
                navigateToUpdateEquipoScreen =
                navigateToUpdateEquipoScreen
            )
        }
    }
}