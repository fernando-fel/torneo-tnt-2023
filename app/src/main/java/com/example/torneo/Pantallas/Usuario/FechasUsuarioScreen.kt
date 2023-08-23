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
import com.example.torneo.Components.AddFechaFlotingActionButton
import com.example.torneo.Components.AddFechasAlertDialog
import com.example.torneo.Components.FechasContent
import com.example.torneo.Components.Usuario.FechasUsuarioContent
import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.TorneoViewModel.FechasViewModel

@Composable
fun FechasUsuarioScreen(
    viewModel: FechasViewModel = hiltViewModel(),
    navController: (idFecha:  Int) -> Unit,
    torneoId: Int,
    navigateToPartidoScreen: (fechaId: Int) -> Unit,
    navControllerBack: NavHostController
){

    val fechas by viewModel.fechas.collectAsState(initial = emptyList() )

    val fechasDeTorneo: List<Fecha> = fechas.filter { fecha -> fecha.idTorneo.toString() == torneoId.toString() }

    Scaffold (
        topBar = {
            CustomTopAppBar(navControllerBack, "Fechas", true)
        },
        content = { padding->
            FechasUsuarioContent(
                padding = padding,
                fechas = fechasDeTorneo,
                deleteFecha={ fecha->
                    viewModel.deleteFecha(fecha)
                },
                navigateToUpdateFechaScreen =
                navController,
                navigateToPartidoScreen
            )
        }
    )
}