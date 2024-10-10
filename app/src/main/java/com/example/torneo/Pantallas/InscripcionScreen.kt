package com.example.torneo.Pantallas

import Component.CustomTopAppBar
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.torneo.TorneoViewModel.EquiposViewModel
import com.example.torneo.TorneoViewModel.TorneosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InscripcionScreen(
    torneoId: Int,
    navController: (Int) -> Unit,
    navControllerBack: NavHostController,
    viewModel: EquiposViewModel = hiltViewModel(),
    viewModel2: TorneosViewModel = hiltViewModel()
) {
    val equipos by viewModel.equipos.collectAsState(initial = emptyList())
    //val equiposSeleccionados by viewModel.getfe(torneoId).collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            CustomTopAppBar(navControllerBack, "Inscripción", true)
        },
        content = { padding ->
            InscripcionContent(
                padding = padding,
                equipos = equipos,
                onInscribir = { equiposSeleccionados ->
                    viewModel.inscribirEquipos(equiposSeleccionados, torneoId)
                    navController(torneoId)
                },
                onShowToast = { message ->
                    Toast.makeText(LocalContext.current, message, Toast.LENGTH_SHORT).show()
                }
            )
        }
    )
}

@Composable
fun InscripcionContent(
    padding: PaddingValues,
    equipos: List<com.example.torneo.Core.Data.Entity.Equipo>,
    onInscribir: (List<com.example.torneo.Core.Data.Entity.Equipo>) -> Unit,
    onShowToast: @Composable (String) -> Unit
) {
    var selectedEquipos by remember { mutableStateOf(setOf<com.example.torneo.Core.Data.Entity.Equipo>()) } // Cambiado a un Set

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
    ) {
        Text(text = "Selecciona los Equipos para Inscribir", style = MaterialTheme.typography.titleMedium)

        // Mostrar el número de equipos seleccionados
        Text(text = "${selectedEquipos.size} equipos seleccionados", style = MaterialTheme.typography.bodyMedium)

        LazyColumn(
            modifier = Modifier.fillMaxHeight(0.8f)
        ) {
            items(equipos) { equipo ->
                val isSelected = selectedEquipos.contains(equipo)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            if (isSelected) {
                                selectedEquipos = selectedEquipos - equipo // Eliminar del Set
                            } else {
                                selectedEquipos = selectedEquipos + equipo // Agregar al Set
                            }
                        }
                        .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.background),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                selectedEquipos = selectedEquipos + equipo // Agregar al Set
                            } else {
                                selectedEquipos = selectedEquipos - equipo // Eliminar del Set
                            }
                        },
                        colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                    )
                    Text(text = equipo.nombre, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                    onInscribir(selectedEquipos.toList()) // Convertir a lista para inscribir
                    //selectedEquipos = emptySet() // Limpiar selección
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedEquipos.size >= 2
        ) {
            Text(text = "Inscribir Equipos")
        }
    }
}
