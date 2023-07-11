package com.example.torneo.Components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.Core.Data.Entity.Partido
import com.example.torneo.Core.Data.Entity.Torneo
import com.example.torneo.Core.Data.repository.Partidos
import com.example.torneo.Core.Data.repository.Torneos

@Composable
fun PartidosContent (
    padding: PaddingValues,
    partidos : Partidos,
    deletePartido: (partido: Partido)->Unit,
    navigateToUpdatePartidoScreen: (partidoId: Int)-> Unit

){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ){
        items(partidos){ partido->
            PartidoCard(
                partido = partido,
                deletePartido={
                    deletePartido(partido)
                },
                navigateToUpdatePartidoScreen =
                navigateToUpdatePartidoScreen
            )
        }
    }
}



