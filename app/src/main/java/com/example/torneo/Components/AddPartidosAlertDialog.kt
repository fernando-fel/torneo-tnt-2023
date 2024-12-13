package com.example.torneo.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Partido
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.TorneoViewModel.EquiposViewModel
import com.example.torneo.TorneoViewModel.PersonasViewModel
import com.example.torneo.TorneoViewModel.TorneosViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AddPartidosDialog(
    viewModel: EquiposViewModel = hiltViewModel(),
    viewModel3: TorneosViewModel = hiltViewModel(),
    viewModel2: PersonasViewModel = hiltViewModel(),
    fechaId: Int,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    addPartido: (partido: Partido) -> Unit
) {
    if (openDialog) {
        var datePickerExpanded by rememberSaveable { mutableStateOf(false) }
        var timePickerExpanded by rememberSaveable { mutableStateOf(false) }
        var selectedDate by rememberSaveable { mutableStateOf<Date?>(null) }
        var selectedTime by rememberSaveable { mutableStateOf("") }
        var local by rememberSaveable { mutableStateOf<Equipo?>(null) }
        var visitante by rememberSaveable { mutableStateOf<Equipo?>(null) }
        var numeroCancha by rememberSaveable { mutableStateOf("") }
        var juez by rememberSaveable { mutableStateOf<Persona?>(null) }
        /*
        var datePickerExpanded by remember { mutableStateOf(false) }
        var timePickerExpanded by remember { mutableStateOf(false) }
        var selectedDate by remember { mutableStateOf<Date?>(null) }
        var selectedTime by remember { mutableStateOf("") }
        var local by remember { mutableStateOf<Equipo?>(null) }
        var visitante by remember { mutableStateOf<Equipo?>(null) }
        var numeroCancha by remember { mutableStateOf("") }
        var juez by remember { mutableStateOf<Persona?>(null) }
        */

        AlertDialog(
            onDismissRequest = { closeDialog() },
            properties = DialogProperties(usePlatformDefaultWidth = false),
            modifier = Modifier.fillMaxWidth()
        ) {
            Surface(
                modifier = Modifier
                    .width(400.dp) // Ajusta el ancho del diálogo aquí
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Agregar Partido", style = MaterialTheme.typography.h4)
                    Spacer(modifier = Modifier.height(16.dp))

                    val personas by viewModel2.personas.collectAsState(initial = emptyList())
                    val jueces = personas.filter { it.rol == "juez" }
                    //val equipos by viewModel.equipos.collectAsState(initial = emptyList())
                    val equipos by viewModel.recuperarEquiposTorneo(fechaId).collectAsState(initial = emptyList())
                    // Filtra los equipos para que el equipo seleccionado no aparezca en la otra lista
                    val equiposDisponiblesParaLocal = equipos.filter { it != visitante }
                    val equiposDisponiblesParaVisitante = equipos.filter { it != local }
                    // Selector de cancha
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Nombre de la Cancha",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        TextField(
                            value = numeroCancha,
                            onValueChange = { numeroCancha = it },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { datePickerExpanded = true }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Fecha")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = selectedDate?.let { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it) } ?: "Selecciona una fecha",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    if (datePickerExpanded) {
                        DatePickerDialog(
                            onDismissRequest = { datePickerExpanded = false },
                            onDateSelected = { date ->
                                selectedDate = date
                                datePickerExpanded = false
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { timePickerExpanded = true }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Filled.AccessTime, contentDescription = "Hora")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (selectedTime.isNotEmpty()) selectedTime else "Selecciona una hora",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (timePickerExpanded) {
                        TimePickerDialog(
                            onDismissRequest = { timePickerExpanded = false },
                            onTimeSelected = { hour, minute ->
                                selectedTime = String.format("%02d:%02d", hour, minute)
                                timePickerExpanded = false
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Equipo Local*",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        EditableExposedDropdownMenuSample(equipos = equiposDisponiblesParaLocal) { selectedLocal ->
                            local = selectedLocal
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Equipo Visitante*",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        EditableExposedDropdownMenuSample(equipos = equiposDisponiblesParaVisitante) { selectedVisitante ->
                            visitante = selectedVisitante
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Juez*",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        ObtenerJuez(jueces = jueces) { selectedJuez ->
                            juez = selectedJuez
                        }
                    }

                    // Función de validación
                    fun isFormValid(): Boolean {
                        return local != null &&
                                visitante != null &&
                                numeroCancha.isNotEmpty() &&
                                juez != null
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = closeDialog) {
                            Text("Cancelar")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(
                            onClick = {
                                if (isFormValid()) {
                                    closeDialog()
                                    val partido = Partido(
                                        resultado = " -    -",
                                        numCancha = numeroCancha,
                                        idVisitante = visitante!!.id.toString(),
                                        idLocal = local!!.id.toString(),
                                        idFecha = fechaId.toString(),
                                        hora = selectedTime,
                                        dia = selectedDate?.let { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it) } ?: "",
                                        golVisitante = 0,
                                        golLocal = 0,
                                        estado = "Programado",
                                        id = 0,
                                        idPersona = juez!!.id.toString()
                                    )
                                    addPartido(partido)
                                }
                            },
                            enabled = isFormValid()
                        ) {

                            Text("Agregar Partido")
                        }
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateSelected: (date: Date) -> Unit,
    state: DatePickerState = rememberDatePickerState()
) {
    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismissRequest,
        title = { Text("Selecciona la fecha") },
        text = {
                DatePicker(
                    state = state,
                    modifier = Modifier.fillMaxWidth()
                )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val selectedDateMillis = state.selectedDateMillis ?: return@TextButton
                    // Utiliza Calendar para ajustar la fecha
                    val calendar = Calendar.getInstance().apply {
                        timeInMillis = selectedDateMillis
                    }
                    // Asegúrate de que estás trabajando con la fecha correcta
                    val selectedDate = calendar.time
                    onDateSelected(selectedDate)
                    onDismissRequest()
                }

            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancelar")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onTimeSelected: (hour: Int, minute: Int) -> Unit
) {
    val timePickerState = rememberTimePickerState()

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Selecciona la hora") },
        text = {
            TimePicker(
                state = timePickerState,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val hour = timePickerState.hour
                    val minute = timePickerState.minute
                    onTimeSelected(hour, minute)
                    onDismissRequest()
                }
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancelar")
            }
        }
    )
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditableExposedDropdownMenuSample(
    equipos: List<Equipo>,
    onEquipoSelected: (Equipo) -> Unit // Callback para devolver el objeto seleccionado
) {
    var selectedOption by remember { mutableStateOf<Equipo?>(null) }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedOption?.nombre ?: "",
                singleLine = true,
                onValueChange = { /* No se usa aquí */ },
                label = { Text("Equipo") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            val filteringOptions = equipos.filter {
                it.nombre.contains(selectedOption?.nombre ?: "", ignoreCase = true)
            }

            if (filteringOptions.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    filteringOptions.forEach { equipo ->
                        DropdownMenuItem(
                            onClick = {
                                selectedOption = equipo
                                onEquipoSelected(equipo) // Devolver el objeto seleccionado
                                expanded = false
                            }
                        ) {
                            Text("${equipo.nombre}")
                        }
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ObtenerJuez(
    jueces: List<Persona>,
    onJuezSelected: (Persona) -> Unit // Callback para devolver el objeto seleccionado
) {
    var selectedOption by remember { mutableStateOf<Persona?>(null) }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedOption?.nombre ?: "",
                singleLine = true,
                onValueChange = { /* No se usa aquí */ },
                label = { Text("Juez") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            val filteringOptions = jueces.filter {
                it.nombre.contains(selectedOption?.nombre ?: "", ignoreCase = true)
            }

            if (filteringOptions.isNotEmpty()) {
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    filteringOptions.forEach { juez ->
                        DropdownMenuItem(
                            onClick = {
                                selectedOption = juez
                                onJuezSelected(juez) // Devolver el objeto seleccionado
                                expanded = false
                            }
                        ) {
                            Text("${juez.nombre}")
                        }
                    }
                }
            }
        }
    }
}
