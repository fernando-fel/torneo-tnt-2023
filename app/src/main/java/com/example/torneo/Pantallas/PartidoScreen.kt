package com.example.torneo.Pantallas

import Component.CustomTopAppBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

import com.example.torneo.Components.AddPartidosDialog
import com.example.torneo.Components.AddTorneoFlotingActionButton

import com.example.torneo.Components.PartidosContent
import com.example.torneo.Core.Data.Entity.Fecha

import com.example.torneo.R
import com.example.torneo.TorneoViewModel.PartidosViewModel

import com.example.torneo.Notificaciones.notificacionBasica
import com.example.torneo.Notificaciones.crearCanalNotificacion
import com.example.torneo.TorneoViewModel.FechasViewModel


@Composable
fun PartidoScreen(    viewModel2 : FechasViewModel = hiltViewModel(),
    viewModel: PartidosViewModel = hiltViewModel(),
    navController: (partidoId: Int) -> Unit,
    fechaId: Int,
    navControllerBack: NavHostController
){
    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldWithTopBarPartidosScreen(viewModel2,viewModel, navController, fechaId, navControllerBack)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBarPartidosScreen(
    viewModel2 : FechasViewModel = hiltViewModel(),
    viewModel: PartidosViewModel = hiltViewModel(),
    navController: (partidoId: Int) -> Unit,
    fechaId: Int,
    navControllerBack: NavHostController
) {
    val fecha by viewModel2.getFecha2(fechaId).collectAsState(initial = null)
    val torneoId = fecha?.idTorneo?.toString()?.toInt()
    val partidos by viewModel.partidos.collectAsState(initial = emptyList())
    var partidosDeFecha =
        partidos.filter { partido -> partido.idFecha.toString() == fechaId.toString() }
    val context = LocalContext.current
    val idChannel = "idFechas"
    val idNotificationBase = 0

    LaunchedEffect(Unit) {
        crearCanalNotificacion(idChannel, context)
    }
    Scaffold(
        topBar = {
            CustomTopAppBar(navControllerBack, "Partidos", true)
        },
        content = { padding ->
            PartidosContent(
                padding = padding,
                partidos = partidosDeFecha,
                deletePartido = { partido ->
                    viewModel.deletePartido(partido)
                },
                navigateToUpdatePartidoScreen = navController
            )
            AddPartidosDialog(
                fechaId = fechaId,
                openDialog = viewModel.openDialog,
                closeDialog = {
                    viewModel.closeDialog()
                },
                addPartido = { partido ->
                    viewModel.addPartido(partido)
                }
            )
        },
        floatingActionButton = {
            Row {
                IconButton(onClick = {
                    torneoId?.let {
                        notificacionBasica(
                            context,
                            idChannel,
                            idNotificationBase,
                            "Hay una nueva Fecha",
                            "¡Holaaa! se creo la nueva fecha! Entra a ver los partidos",
                            it,
                        )
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notificación"
                    )
                }
                AddTorneoFlotingActionButton(
                    openDialog = {
                        viewModel.openDialog()
                    }
                )
            }
        }
    )
}