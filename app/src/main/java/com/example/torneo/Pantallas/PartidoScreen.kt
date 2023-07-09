package com.example.torneo.Pantallas

import Component.CustomTopAppBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.torneo.Components.AddPartidosAlertDialog
import com.example.torneo.Components.AddTorneoFlotingActionButton
import com.example.torneo.Components.AddTorneosAlertDialog
import com.example.torneo.Components.PartidosContent
import com.example.torneo.Components.TorneosContent
import com.example.torneo.Core.Constantes.Companion.TORNEOS_SCREEN
import com.example.torneo.TorneoViewModel.PartidosViewModel
import com.example.torneo.TorneoViewModel.TorneosViewModel

@Composable
fun PartidoScreen(
    viewModel: PartidosViewModel = hiltViewModel(),
    navController: (partidoId: Int) -> Unit,
    fechaId: Int,
    navControllerBack: NavHostController
){
    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldWithTopBarPartidosScreen(viewModel, navController, fechaId, navControllerBack)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBarPartidosScreen(
    viewModel: PartidosViewModel = hiltViewModel(),
    navController: (partidoId: Int) -> Unit,
    fechaId: Int,
    navControllerBack: NavHostController
){
    val partidos by viewModel.partidos.collectAsState( initial = emptyList() )

    Scaffold (
        topBar = {
            CustomTopAppBar(navControllerBack, "Partidos", true)
        },
        content = { padding->
            PartidosContent(
                padding = padding,
                partidos = partidos,
                deletePartido={
                        partido->
                    viewModel.deletePartido(partido)
                },
                navigateToUpdatePartidoScreen =  navController
            )
            AddPartidosAlertDialog(
                fechaId = fechaId,
                openDialog = viewModel.openDialog,
                closeDialog = {
                    viewModel.closeDialog()
                },
                addPartido = {partido->
                    viewModel.addPartido(partido)

                }
            )
        },
        floatingActionButton = {
            AddTorneoFlotingActionButton(
                openDialog = {
                    viewModel.openDialog()
                }
            )
        }
    )
}