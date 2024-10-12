package com.example.torneo.Pantallas.Usuario

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
import androidx.compose.material.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.torneo.Components.Usuario.TorneosUsuarioContent
import com.example.torneo.TorneoViewModel.TorneosViewModel

@Composable
fun TorneosUsuarioScreen(
    viewModel: TorneosViewModel = hiltViewModel(),
    navController: (torneoId: Int) -> Unit,
    navigateToFechaScreen: (torneoId: Int) -> Unit,
    navControllerBack: NavHostController
){
    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldWithTopBarTorneosUsuarioScreen(viewModel, navController, navigateToFechaScreen, navControllerBack)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBarTorneosUsuarioScreen(
    viewModel: TorneosViewModel = hiltViewModel(),
    navController: (torneoId: Int) -> Unit,
    navigateToFechaScreen: (torneoId: Int) -> Unit,
    navControllerBack: NavHostController,
){

    val torneos by viewModel.torneos.collectAsState(initial = emptyList() )

    var mostrarFinalizados by remember { mutableStateOf(false) }
    var mostrarEnCurso by remember { mutableStateOf(false) }
    var mostrarTodos by remember { mutableStateOf(false) }

    fun actualizarPestañas(todos: Boolean, enCurso: Boolean, finalizados: Boolean) {
        mostrarTodos = todos
        mostrarEnCurso = enCurso
        mostrarFinalizados = finalizados
    }

    Scaffold (
        topBar = {
            CustomTopAppBar(navControllerBack, "Torneos", true)
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                var selectedTabIndex by remember { mutableStateOf(0) }
                val tabs = listOf("Todos", "En curso", "Finalizados")

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
            TorneosUsuarioContent(
                padding = padding,
                torneos = torneos,
                deleteTorneo = { torneo ->
                    viewModel.deleteTorneo(torneo)
                },
                navigateToUpdateTorneoScreen = navController,
                navigateToFechaScreen, mostrarTodos, mostrarFinalizados,
                mostrarEnCurso
            )
            }
        }
    )
}



