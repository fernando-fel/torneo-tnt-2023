package com.example.torneo.Pantallas.Usuario

import Component.CustomTopAppBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.torneo.Components.Usuario.MenuBottomBar
import com.example.torneo.Components.Usuario.TorneosUsuarioContent
import com.example.torneo.TorneoViewModel.TorneosViewModel

@Composable
fun TorneosUsuarioScreen(
    viewModel: TorneosViewModel = hiltViewModel(),
    navController: (torneoId: Int) -> Unit,
    navigateToFechaScreen: (torneoId: Int) -> Unit,
    navControllerBack: NavHostController
) {
    val torneos by viewModel.torneos.collectAsState(initial = emptyList())
    var mostrarFinalizados by remember { mutableStateOf(false) }
    var mostrarEnCurso by remember { mutableStateOf(false) }
    var mostrarTodos by remember { mutableStateOf(true) }

    fun actualizarPestañas(todos: Boolean, enCurso: Boolean, finalizados: Boolean) {
        mostrarTodos = todos
        mostrarEnCurso = enCurso
        mostrarFinalizados = finalizados
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(navControllerBack, "Mis Torneos", true)
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Título y descripción
                /*Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Gestión de Torneos",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Ver torneos",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }*/

                // Tabs mejorados
                var selectedTabIndex by remember { mutableIntStateOf(0) }
                val tabs = listOf(
                    TabItem("Todos", "Ver todos los torneos"),
                    TabItem("En Curso", "Torneos activos"),
                    TabItem("Finalizados", "Torneos completados")
                )

                //Spacer(modifier = Modifier.height(16.dp))

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                .padding(horizontal = 24.dp),
                            height = 3.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary,
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, tab ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = {
                                selectedTabIndex = index
                                when (index) {
                                    0 -> actualizarPestañas(
                                        todos = true,
                                        enCurso = false,
                                        finalizados = false
                                    )
                                    1 -> actualizarPestañas(
                                        todos = false,
                                        enCurso = true,
                                        finalizados = false
                                    )
                                    2 -> actualizarPestañas(
                                        todos = false,
                                        enCurso = false,
                                        finalizados = true
                                    )
                                }
                            },
                            modifier = Modifier.padding(vertical = 8.dp),
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text(
                                    text = tab.title,
                                    fontSize = 14.sp,
                                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedTabIndex == index)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }

                //Spacer(modifier = Modifier.height(16.dp))

                // Contenido principal
                TorneosUsuarioContent(
                    padding = PaddingValues(horizontal = 16.dp),
                    torneos = torneos,
                    deleteTorneo = { torneo ->
                        viewModel.deleteTorneo(torneo)
                    },
                    navigateToUpdateTorneoScreen = navController,
                    navegarParaUnaFecha = navigateToFechaScreen,
                    mostrarTodos = mostrarTodos,
                    mostrarFinalizados = mostrarFinalizados,
                    mostrarEnCurso = mostrarEnCurso
                )
            }
        },
        bottomBar = {
            MenuBottomBar(navControllerBack)
        }
        //containerColor = MaterialTheme.colorScheme.background
    )
}

data class TabItem(
    val title: String,
    val description: String
)