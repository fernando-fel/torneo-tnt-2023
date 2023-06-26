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
import com.example.torneo.Components.AddFechaFlotingActionButton
import com.example.torneo.Components.AddFechasAlertDialog
import com.example.torneo.Components.AddTorneoFlotingActionButton
import com.example.torneo.Components.AddTorneosAlertDialog
import com.example.torneo.Components.FechasContent
import com.example.torneo.Components.TorneosContent
import com.example.torneo.Core.Constantes.Companion.TORNEOS_SCREEN
import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.TorneoViewModel.FechasViewModel
import com.example.torneo.TorneoViewModel.TorneosViewModel

@Composable
fun FechasScreen(
    viewModel: FechasViewModel = hiltViewModel(),
    navController: (idFecha:  Int) -> Unit
){
    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldWithTopBarFechasScreen(viewModel, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBarFechasScreen(
    viewModel: FechasViewModel = hiltViewModel(),
    navController: (fechasId: Int) -> Unit
){
    val fechas by viewModel.fechas.collectAsState(
        initial = emptyList() )
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text("Fechas")
                })
        },
        content = { padding->
            FechasContent(
                padding = padding,
                fechas = fechas,
                deleteFecha={
                        fecha->
                    viewModel.deleteFecha(fecha)
                },
                navigateToUpdateFechaScreen =  navController
            )
            AddFechasAlertDialog(
                openDialog = viewModel.openDialog,
                closeDialog = {
                    viewModel.closeDialog()
                },
                addFecha = {fecha->
                    viewModel.addFecha(fecha)

                }
            )
        },
        floatingActionButton = {
            AddFechaFlotingActionButton(
                openDialog = {
                    viewModel.openDialog()
                }
            )
        }
    )
}