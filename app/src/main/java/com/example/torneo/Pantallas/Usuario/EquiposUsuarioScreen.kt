
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
import com.example.torneo.TorneoViewModel.EquiposViewModel

@Composable
fun EquiposUsuarioScreen(
    viewModel: EquiposViewModel = hiltViewModel(),
    navController: (equipoId: Int) -> Unit,
    navControllerBack: NavHostController
){
    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldWithTopBarEquipoUsuarioScreen(viewModel, navController, navControllerBack)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBarEquipoUsuarioScreen(
    viewModel: EquiposViewModel = hiltViewModel(),
    navController: (EquipoId: Int) -> Unit,
    navControllerBack: NavHostController
){
    val equipos by viewModel.equipos.collectAsState(
        initial = emptyList() )
    Scaffold (
        topBar = {
            CustomTopAppBar(navControllerBack, "Equipos", true)
        },
        content = { padding->
            EquipoUsuarioContent(
                padding = padding,
                equipos = equipos,
                deleteEquipo={
                        equipo->
                    viewModel.deleteEquipo(equipo)
                },
                navigateToUpdateEquipoScreen =  navController
            )
        }
    )
}