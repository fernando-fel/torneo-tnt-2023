package com.example.torneo.Pantallas

import Component.CustomTopAppBar
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.torneo.Components.FechasContent
import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.TorneoViewModel.FechasViewModel

@Composable
fun FechasScreen(
    viewModel: FechasViewModel = hiltViewModel(),
    navController: (idFecha: Int) -> Unit,
    torneoId: String,
    navigateToPartidoScreen: (fechaId: Int) -> Unit,
    navControllerBack: NavHostController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        ScaffoldWithTopBarFechasScreen(
            torneoId = torneoId,
            viewModel = viewModel,
            navController = navController,
            navigateToPartidoScreen = navigateToPartidoScreen,
            navControllerBack = navControllerBack
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithTopBarFechasScreen(
    torneoId: String,
    viewModel: FechasViewModel = hiltViewModel(),
    navController: (fechasId: Int) -> Unit,
    navigateToPartidoScreen: (fechaId: Int) -> Unit,
    navControllerBack: NavHostController
) {
    val fechas by viewModel.fechas.collectAsState()
    val fechasDeTorneo = fechas.filter { it.idTorneo == torneoId.toInt()}

    Scaffold(
        topBar = {
            CustomTopAppBar(navControllerBack, "Fechas", true)
        },
        content = { padding ->
            FechasContent(
                padding = padding,
                fechas = fechasDeTorneo,
                deleteFecha = { fecha -> viewModel.deleteFecha(fecha) },
                navigateToUpdateFechaScreen = navController,
                navegarParaPartidos = navigateToPartidoScreen
            )
        },
        floatingActionButton = {
            AddFechaFlotingActionButton(
                onClick = {
                    // Calcular el próximo número de fecha y crear una nueva fecha
                    val newFechaNumber = viewModel.getNextFechaNumber(torneoId)
                    Log.d("FechasScreen", "Next fecha number: $newFechaNumber")

                    // Crear una nueva fecha
                    val nuevaFecha = Fecha(
                        id = 0, // Deberías manejar el ID adecuadamente o usar una estrategia para generar IDs únicos
                        idTorneo = torneoId.toInt(),
                        numero = newFechaNumber.toString(),
                        estado = "programado"
                    )
                    Log.d("FechasScreen", "New fecha created: $nuevaFecha")

                    // Agregar la nueva fecha usando el ViewModel
                    viewModel.addFecha(nuevaFecha)
                }
            )
        }
    )
}
@Composable
fun AddFechaFlotingActionButton(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(Icons.Filled.Add, contentDescription = "Add Fecha")
    }
}