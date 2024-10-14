package com.example.torneo.Pantallas

import Component.CustomTopAppBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.MaterialTheme

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow

import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
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
    navigateToInscripcionTorneoScreen: (torneoId: Int) -> Unit,
    navControllerBack: NavHostController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldWithTopBarTorneosScreen(viewModel, navController, navigateToFechaScreen,navigateToInscripcionTorneoScreen, navControllerBack)
    }
}

@Composable
fun ScaffoldWithTopBarTorneosScreen(
    viewModel: TorneosViewModel = hiltViewModel(),
    navController: (torneoId: Int) -> Unit,
    navigateToFechaScreen: (torneoId: Int) -> Unit,
    navigateToInscripcionTorneoScreen: (torneoId: Int) -> Unit,
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
                var selectedTabIndex by remember { mutableStateOf(0) }
                val tabs = listOf("Todos", "En Curso", "Finalizados")

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    indicator = { tabPositions ->
                        SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            height = 5.dp, // Altura del indicador
                            color = MaterialTheme.colorScheme.primary // Color del indicador
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title) },
                            selected = selectedTabIndex == index,
                            onClick = {
                                selectedTabIndex = index
                                when (index) {
                                    0 -> actualizarPestañas(true, false, false)
                                    1 -> actualizarPestañas(false, true, false)
                                    2 -> actualizarPestañas(false, false, true)
                                }
                            },
                            selectedContentColor = Color.Black,
                            unselectedContentColor = Color.Black
                        )
                    }
                }

                TorneosContent(
                    //padding = padding,
                    torneos = torneos,
                    deleteTorneo = { torneo ->
                        viewModel.deleteTorneo(torneo)
                    },
                    navigateToUpdateTorneoScreen = navController,
                    navigateToFechaScreen,
                    navigateToInscripcionTorneoScreen,
                    mostrarTodos, mostrarFinalizados, mostrarEnCurso,
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
@Composable
fun SecondaryIndicator(
    modifier: Modifier = Modifier,
    height: Dp = 2.dp,
    color: Color = Color.Blue
) {
    TabRowDefaults.Indicator(
        modifier = modifier.height(height),
        color = color,
        //shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50))
    )
}