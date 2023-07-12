package com.example.torneo.Pantallas.Usuario

import Component.CustomTopAppBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.torneo.Components.AddEquipoFlotingActionButton
import com.example.torneo.Components.AddEquiposAlertDialog
import com.example.torneo.Components.EquipoContent
import com.example.torneo.Components.Usuario.EquipoUsuarioContent
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.Core.Data.Entity.Partido
import com.example.torneo.TorneoViewModel.EquiposViewModel

@Composable
fun EquiposUsuarioScreen(
    viewModel: EquiposViewModel = hiltViewModel(),
    //navController: (equipoId:  Int) -> Unit,
    //equipoId: Int,
    navegarApartidosEquipo: (equipoId: Int) -> Unit,
    navControllerBack: NavHostController
){
    val equipos by viewModel.equipos.collectAsState(initial = emptyList() )

    //val partidosDeEquipo: List<Equipo> = equipos.filter { equipo -> equipo.id == equipoId }

    Scaffold (
        topBar = {
            CustomTopAppBar(navControllerBack, "Equipos", true)
        },
        content = { padding->
            EquipoUsuarioContent(
                padding = padding,
                equipos = equipos,
                navegarAPartidosDeEquipo =
                navegarApartidosEquipo
                /*deleteEquipo={
                        equipo->
                    viewModel.deleteEquipo(equipo)
                },
                navigateToUpdateEquipoScreen =  navController*/
            )
        }
    )
}