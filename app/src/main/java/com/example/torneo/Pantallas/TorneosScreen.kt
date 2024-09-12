package com.example.torneo.Pantallas

import Component.CustomTopAppBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.torneo.Components.AddTorneoFlotingActionButton
import com.example.torneo.Components.AddTorneosAlertDialog
import com.example.torneo.Components.TorneosContent
import com.example.torneo.TorneoViewModel.TorneosViewModel

@Composable
fun TorneosScreen(
    viewModel: TorneosViewModel = hiltViewModel(),
    navController: (torneoId: Int) -> Unit,
    navigateToFechaScreen: (torneoId: Int) -> Unit,
    navControllerBack: NavHostController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldWithTopBarTorneosScreen(viewModel, navController, navigateToFechaScreen, navControllerBack)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBarTorneosScreen(
    viewModel: TorneosViewModel = hiltViewModel(),
    navController: (torneoId: Int) -> Unit,
    navigateToFechaScreen: (torneoId: Int) -> Unit,
    navControllerBack: NavHostController
) {
    val torneos by viewModel.torneos.collectAsState(initial = emptyList())

    var mostrarFinalizados by remember { mutableStateOf(false) }
    var mostrarEnCurso by remember { mutableStateOf(false) }
    var mostrarTodos by remember { mutableStateOf(true) } // Iniciar con "Todos" seleccionado

    fun actualizarPestañas(todos: Boolean, enCurso: Boolean, finalizados: Boolean) {
        mostrarTodos = todos
        mostrarEnCurso = enCurso
        mostrarFinalizados = finalizados
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(navControllerBack, "Torneos", true)
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp), // Espacio arriba y abajo del LazyRow
                    verticalAlignment = Alignment.CenterVertically // Centra verticalmente
                ) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        item {
                            Button(
                                onClick = { actualizarPestañas(true, false, false) },

                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (mostrarTodos) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Text("Todos")
                            }
                        }
                        item {
                            Button(
                                onClick = { actualizarPestañas(false, true, false) },

                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (mostrarEnCurso) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Text("En curso")
                            }
                        }
                        item {
                            Button(
                                onClick = { actualizarPestañas(false, false, true) },

                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (mostrarFinalizados) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Text("Finalizados")
                            }
                        }
                    }
                }

                TorneosContent(
                    padding = padding,
                    torneos = torneos,
                    deleteTorneo = { torneo ->
                        viewModel.deleteTorneo(torneo)
                    },
                    navigateToUpdateTorneoScreen = navController,
                    navigateToFechaScreen,
                    mostrarTodos, mostrarFinalizados, mostrarEnCurso
                )
                AddTorneosAlertDialog(
                    openDialog = viewModel.openDialog,
                    closeDialog = {
                        viewModel.closeDialog()
                    },
                    addTorneo = { torneo ->
                        viewModel.addTorneo(torneo)
                    }
                )
            }
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