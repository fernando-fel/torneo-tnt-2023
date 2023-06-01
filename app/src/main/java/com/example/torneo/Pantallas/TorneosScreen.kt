package com.example.torneo.Pantallas

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
import com.example.torneo.Components.AddTorneoFlotingActionButton
import com.example.torneo.Components.AddTorneosAlertDialog
import com.example.torneo.Components.TorneosContent
import com.example.torneo.Core.Constantes.Companion.TORNEOS_SCREEN
import com.example.torneo.TorneoViewModel.TorneosViewModel

@Composable
fun TorneosScreen(
    viewModel: TorneosViewModel = hiltViewModel(),
    navController: (torneoId: Int) -> Unit
){
    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldWithTopBarTorneosScreen(viewModel, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBarTorneosScreen(
    viewModel: TorneosViewModel = hiltViewModel(),
    navController: (torneoId: Int) -> Unit
){
    val torneos by viewModel.torneos.collectAsState(
        initial = emptyList() )
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(TORNEOS_SCREEN)
                })
        },
        content = { padding->
            TorneosContent(
                padding = padding,
                torneos = torneos,
                deleteTorneo={
                    torneo->
                    viewModel.deleteTorneo(torneo)
                },
                navigateToUpdateTorneoScreen =  navController
            )
            AddTorneosAlertDialog(
                openDialog = viewModel.openDialog,
                closeDialog = {
                    viewModel.closeDialog()
                },
                addTorneo = {torneo->
                    viewModel.addTorneo(torneo)

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