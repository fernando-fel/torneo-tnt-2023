package com.example.torneo.Components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.torneo.Core.Constantes.Companion.DISMISS
import com.example.torneo.Core.Constantes.Companion.NO_VALUE
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Partido
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.TorneoViewModel.EquiposViewModel
import com.example.torneo.TorneoViewModel.PersonasViewModel
import kotlinx.coroutines.Job
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.reflect.typeOf

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun AddPartidosAlertDialog(
    viewModel: EquiposViewModel = hiltViewModel(),
    viewModel2: PersonasViewModel = hiltViewModel(),
    fechaId: Int,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    addPartido: (partido: Partido) -> Unit
) {

    val personas by viewModel2.personas.collectAsState(initial = emptyList())
    val jueces: List<Persona> = personas.filter{ juez -> juez.rol == "juez" }

    val equipos by viewModel.equipos.collectAsState(initial = emptyList())
    if (openDialog) {
        var filterText by remember { mutableStateOf("") }
        var selectedOption by remember { mutableStateOf("") }
        var fecha by remember { mutableStateOf(NO_VALUE) }
        var hora by remember { mutableStateOf(NO_VALUE) }
        var dia by remember { mutableStateOf(NO_VALUE) }
        var local by remember { mutableStateOf(NO_VALUE) }
        var visitante by remember { mutableStateOf(NO_VALUE) }
        var numeroCancha by remember { mutableStateOf(NO_VALUE) }
        var golL by remember { mutableStateOf(0) }
        var golV by remember { mutableStateOf(0) }
        var estado by remember { mutableStateOf(NO_VALUE) }
        var juez by remember { mutableStateOf(NO_VALUE) }

        var fechaDePrueba = rememberDatePickerState(initialDisplayMode = DisplayMode.Input, initialSelectedDateMillis = 1578096000000)

        val focusRequester = FocusRequester()
        val keyboardController = LocalSoftwareKeyboardController.current
        var formattedDate  by remember {mutableStateOf("")}
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        AlertDialog(
            onDismissRequest = { closeDialog() },
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            title = {
                Text("Agregar Partido")
            },
            text = {
                Column() {
                    /*TextField(
                        label = { Text(text = "Día del partido") },
                        singleLine = true,
                        value = dia,
                        onValueChange = { dia = it },
                        modifier = Modifier.focusRequester(focusRequester)
                    )
                    */
                    DatePicker(state = fechaDePrueba)
                    Text("Entered date timestamp: ${fechaDePrueba.selectedDateMillis.toString() ?: "no selection"}")
                    Log.d("Fecha deprueba", fechaDePrueba.toString())
                    Spacer(modifier = Modifier.height(16.dp).focusRequester(focusRequester))
                    fechaDePrueba.selectedDateMillis?.let { millis ->
                        val dateF = Date(millis)
                        formattedDate = dateFormat.format(dateF)}
                    //val date = Date(fechaDePrueba.selectedDateMillis)
                    // formattedDate = dateFormat.format(date)
                    Log.d("fecha ***********", formattedDate.toString())

                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(
                        label = { Text(text = "Hora del partido") },
                        singleLine = true,
                        value = hora,
                        onValueChange = { hora = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Agregando DatePicker
                    //var fechaDePrueba = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

                    local = EditableExposedDropdownMenuSample(equipos = equipos)

                    Log.d("Local", local.toString())

                    visitante = EditableExposedDropdownMenuSample(equipos = equipos)

                    Log.d("Visitante", visitante.toString())

/*
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        label = { Text(text = "Equipo Visitante") },
                        singleLine = true,
                        value = visitante,
                        onValueChange = { visitante = it }
                    )*/
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        label = { Text(text = "Nombre de la Cancha") },
                        singleLine = true,
                        value = numeroCancha,
                        onValueChange = { numeroCancha = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    juez = ObtenerJuez(jueces = jueces)

/*                    TextField(
                        label = { Text(text = "Nombre del Juez") },
                        singleLine = true,
                        value = juez,
                        onValueChange = { juez = it }
                    )*/

                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                        keyboardController?.show()
                    }
                }
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        closeDialog()
                        val partido = Partido(
                            resultado = " -    -",
                            numCancha = numeroCancha,
                            idVisitante = visitante,
                            idLocal = local,
                            idFecha = fechaId.toString(),
                            hora = hora,
                            dia = formattedDate,
                            golVisitante = 0,
                            golLocal = 0,
                            estado = "Programado",
                            id = 0,
                            idPersona = juez
                        )
                        addPartido(partido)
                    },
                    enabled = !(hora.isBlank()  || numeroCancha.isBlank() || juez.isBlank() ||
                                local.isBlank() || visitante.isBlank() || formattedDate.toString().isBlank() )
                ) {
                    Text(text = "Agregar Partido")
                }
            },
            dismissButton = {
                TextButton(onClick = closeDialog) {
                    Text(text = DISMISS)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditableExposedDropdownMenuSample(equipos: List<Equipo>): String {
    var selectedOptionText by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        var expanded by remember { mutableStateOf(false) }
        //var selectedOptionText by remember { mutableStateOf("") }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                value = selectedOptionText,
                singleLine = true,
                onValueChange = { selectedOptionText = it },
                label = { Text("Equipo") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )

            val filteringOptions = equipos.filter {
                it.nombre.contains(selectedOptionText, ignoreCase = true)
            }

            if (filteringOptions.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    filteringOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            content = { Text((selectionOption.id.toString()) + " - " + (selectionOption.nombre) ) },
                            onClick = {
                                selectedOptionText = selectionOption.id.toString()
                                expanded = false
                            },
                        )
                    }
                }
            }
        }
    }
    Log.d("Local aca adentro", selectedOptionText.toString())
    return selectedOptionText
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ObtenerJuez(jueces: List<Persona>): String {
    var selectedOptionText by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(16.dp)) {
        var expanded by remember { mutableStateOf(false) }
        //var selectedOptionText by remember { mutableStateOf("") }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                value = selectedOptionText,
                singleLine = true,
                onValueChange = { selectedOptionText = it },
                label = { Text("Juez") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )

            val filteringOptions = jueces.filter {
                it.nombre.contains(selectedOptionText, ignoreCase = true)
            }

            if (filteringOptions.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    filteringOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            content = { Text((selectionOption.id.toString()) + " - " + (selectionOption.nombre) ) },
                            onClick = {
                                selectedOptionText = selectionOption.id.toString()
                                expanded = false
                            },
                        )
                    }
                }
            }
        }
    }
    print(selectedOptionText)

    return selectedOptionText
}