package com.example.torneo.Components

import android.app.TimePickerDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Partido
import com.example.torneo.Core.Data.Entity.Persona
import com.example.torneo.TorneoViewModel.EquiposViewModel
import com.example.torneo.TorneoViewModel.PersonasViewModel
import com.example.torneo.TorneoViewModel.TorneosViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class PartidoField(
    val label: String,
    val icon: ImageVector,
    val value: String,
    val onValueChange: (String) -> Unit,
    val keyboardType: KeyboardType = KeyboardType.Text,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val placeholder: String = "",
    val trailingIcon: @Composable (() -> Unit)? = null
)

@OptIn(ExperimentalMaterial3Api::class)
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
        var numeroCancha by remember { mutableStateOf("") }
        var selectedDate by remember { mutableStateOf<String>("") }
        var selectedTime by remember { mutableStateOf("") }
        var local by remember { mutableStateOf<Equipo?>(null) }
        var visitante by remember { mutableStateOf<Equipo?>(null) }
        var juez by remember { mutableStateOf<Persona?>(null) }

        val focusRequester = FocusRequester()
        val scrollState = rememberScrollState()

        var showDatePicker by remember { mutableStateOf(false) }
        var showTimePicker by remember { mutableStateOf(false) }

        val equipos by viewModel.recuperarEquiposTorneo(fechaId).collectAsState(initial = emptyList())
        val personas by viewModel2.personas.collectAsState(initial = emptyList())
        val jueces = personas.filter { it.rol == "juez" }

        // Validaciones
        val isNumeroCanchaValid = numeroCancha.length >= 1
        val isDateValid = { date: String ->
            try {
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(date)
                true
            } catch (e: Exception) {
                false
            }
        }
        val isTimeValid = { time: String ->
            time.matches(Regex("^([01]\\d|2[0-3]):([0-5]\\d)$"))
        }

        if (showDatePicker) {
            showDatePicker { date ->
                selectedDate = date
                showDatePicker = false
            }
        }

        if (showTimePicker) {
            TimePickerDialog(
                onDismissRequest = { showTimePicker = false },
                onTimeSelected = { hour, minute ->
                    selectedTime = String.format("%02d:%02d", hour, minute)
                    showTimePicker = false
                }
            )
        }

        val fields = listOf(
            PartidoField(
                label = "Número de Cancha",
                icon = Icons.Default.SportsSoccer,
                value = numeroCancha,
                onValueChange = { numeroCancha = it },
                isError = numeroCancha.isNotBlank() && !isNumeroCanchaValid,
                errorMessage = "Ingrese un número de cancha válido",
                placeholder = "Ej: Cancha 1"
            ),
            PartidoField(
                label = "Fecha del Partido",
                icon = Icons.Default.DateRange,
                value = selectedDate,
                onValueChange = { selectedDate = it },
                isError = selectedDate.isNotBlank() && !isDateValid(selectedDate),
                errorMessage = "Formato de fecha inválido (dd/MM/yyyy)",
                placeholder = "dd/MM/yyyy",
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Seleccionar Fecha"
                        )
                    }
                }
            ),
            PartidoField(
                label = "Hora del Partido",
                icon = Icons.Default.AccessTime,
                value = selectedTime,
                onValueChange = { selectedTime = it },
                isError = selectedTime.isNotBlank() && !isTimeValid(selectedTime),
                errorMessage = "Formato de hora inválido (HH:mm)",
                placeholder = "HH:mm",
                trailingIcon = {
                    IconButton(onClick = { showTimePicker = true }) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = "Seleccionar Hora"
                        )
                    }
                }
            )
        )

        AlertDialog(
            onDismissRequest = closeDialog,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Agregar Partido",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    fields.forEach { field ->
                        Column {
                            OutlinedTextField(
                                value = field.value,
                                onValueChange = field.onValueChange,
                                label = { Text(field.label) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = field.icon,
                                        contentDescription = null
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(
                                        if (field == fields.first())
                                            Modifier.focusRequester(focusRequester)
                                        else Modifier
                                    ),
                                isError = field.isError,
                                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                                    keyboardType = field.keyboardType
                                ),
                                placeholder = {
                                    Text(field.placeholder)
                                },
                                singleLine = true,
                                trailingIcon = field.trailingIcon
                            )

                            AnimatedVisibility(visible = field.isError) {
                                Text(
                                    text = field.errorMessage,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                                )
                            }
                        }
                    }

                    // Dropdown para Equipo Local
                    var localExpanded by remember { mutableStateOf(false) }
                    val equiposDisponiblesParaLocal = equipos.filter { it != visitante }
                    Column {
                        Text("Equipo Local", style = MaterialTheme.typography.labelMedium)
                        ExposedDropdownMenuBox(
                            expanded = localExpanded,
                            onExpandedChange = { localExpanded = !localExpanded }
                        ) {
                            OutlinedTextField(
                                value = local?.nombre ?: "",
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Seleccionar Equipo Local") },
                                leadingIcon = { Icon(Icons.Default.Group, contentDescription = null) },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = localExpanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )

                            ExposedDropdownMenu(
                                expanded = localExpanded,
                                onDismissRequest = { localExpanded = false }
                            ) {
                                equiposDisponiblesParaLocal.forEach { equipo ->
                                    DropdownMenuItem(
                                        text = { Text(equipo.nombre) },
                                        onClick = {
                                            local = equipo
                                            localExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Dropdown para Equipo Visitante
                    var visitanteExpanded by remember { mutableStateOf(false) }
                    val equiposDisponiblesParaVisitante = equipos.filter { it != local }
                    Column {
                        Text("Equipo Visitante", style = MaterialTheme.typography.labelMedium)
                        ExposedDropdownMenuBox(
                            expanded = visitanteExpanded,
                            onExpandedChange = { visitanteExpanded = !visitanteExpanded }
                        ) {
                            OutlinedTextField(
                                value = visitante?.nombre ?: "",
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Seleccionar Equipo Visitante") },
                                leadingIcon = { Icon(Icons.Default.Group, contentDescription = null) },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = visitanteExpanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )

                            ExposedDropdownMenu(
                                expanded = visitanteExpanded,
                                onDismissRequest = { visitanteExpanded = false }
                            ) {
                                equiposDisponiblesParaVisitante.forEach { equipo ->
                                    DropdownMenuItem(
                                        text = { Text(equipo.nombre) },
                                        onClick = {
                                            visitante = equipo
                                            visitanteExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Dropdown para Juez
                    var juezExpanded by remember { mutableStateOf(false) }
                    Column {
                        Text("Juez", style = MaterialTheme.typography.labelMedium)
                        ExposedDropdownMenuBox(
                            expanded = juezExpanded,
                            onExpandedChange = { juezExpanded = !juezExpanded }
                        ) {
                            OutlinedTextField(
                                value = juez?.nombre ?: "",
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Seleccionar Juez") },
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = juezExpanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )

                            ExposedDropdownMenu(
                                expanded = juezExpanded,
                                onDismissRequest = { juezExpanded = false }
                            ) {
                                jueces.forEach { persona ->
                                    DropdownMenuItem(
                                        text = { Text(persona.nombre) },
                                        onClick = {
                                            juez = persona
                                            juezExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val partido = Partido(
                            resultado = " -    -",
                            numCancha = numeroCancha,
                            idVisitante = visitante?.id.toString(),
                            idLocal = local?.id.toString(),
                            idFecha = fechaId.toString(),
                            hora = selectedTime,
                            dia = selectedDate,
                            golVisitante = 0,
                            golLocal = 0,
                            estado = "Programado",
                            id = 0,
                            idPersona = juez?.id.toString()
                        )
                        addPartido(partido)
                        closeDialog()
                    },
                    enabled = (local != null &&
                            visitante != null &&
                            juez != null &&
                            selectedDate.isNotBlank() &&
                            selectedTime.isNotBlank() &&
                            numeroCancha.isNotBlank())
                ) {
                    Text("Agregar Partido")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = closeDialog) {
                    Text("Cancelar")
                }
            }
        )

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
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

