package com.example.torneo.Components

import android.app.DatePickerDialog
import android.widget.DatePicker
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.torneo.Core.Constantes.Companion.ADD_TORNEO
import com.example.torneo.Core.Constantes.Companion.DISMISS
import com.example.torneo.Core.Constantes.Companion.NO_VALUE
import com.example.torneo.Core.Data.Entity.Torneo
import kotlinx.coroutines.job
import java.text.SimpleDateFormat
import java.util.*

data class TournamentField(
    val label: String,
    val icon: ImageVector,
    val value: String,
    val onValueChange: (String) -> Unit,
    val keyboardType: KeyboardType = KeyboardType.Text,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val placeholder: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTorneosAlertDialog(
    openDialog: Boolean,
    closeDialog: () -> Unit,
    addTorneo: (torneo: Torneo) -> Unit
) {
    if (openDialog) {
        var nombre by remember { mutableStateOf(NO_VALUE) }
        var ubicacion by remember { mutableStateOf(NO_VALUE) }
        var fechaInicio by remember { mutableStateOf(NO_VALUE) }
        var fechaFin by remember { mutableStateOf(NO_VALUE) }
        var precio by remember { mutableStateOf(NO_VALUE) }

        val focusRequester = FocusRequester()
        val scrollState = rememberScrollState()

        var showDatePickerInicio by remember { mutableStateOf(false) }
        var showDatePickerFin by remember { mutableStateOf(false) }

        // Validaciones
        val isNombreValid = nombre.length >= 3
        val isPrecioValid = precio.toDoubleOrNull() != null
        val isDateValid = { date: String ->
            try {
                SimpleDateFormat("dd/MM/yyyy").parse(date)
                true
            } catch (e: Exception) {
                false
            }
        }

        val fields = listOf(
            TournamentField(
                label = "Nombre del Torneo",
                icon = Icons.Default.EmojiEvents,
                value = nombre,
                onValueChange = { nombre = it },
                isError = nombre.isNotBlank() && !isNombreValid,
                errorMessage = "El nombre debe tener al menos 3 caracteres",
                placeholder = "Ej: Torneo de Verano 2024"
            ),
            TournamentField(
                label = "Ubicación",
                icon = Icons.Default.LocationOn,
                value = ubicacion,
                onValueChange = { ubicacion = it },
                placeholder = "Ej: Estadio Principal"
            ),
            TournamentField(
                label = "Fecha de Inicio",
                icon = Icons.Default.DateRange,
                value = fechaInicio,
                onValueChange = { fechaInicio = it },
                isError = fechaInicio.isNotBlank() && !isDateValid(fechaInicio),
                errorMessage = "Formato de fecha inválido (dd/MM/yyyy)",
                placeholder = "dd/MM/yyyy"
            ),
            TournamentField(
                label = "Fecha de Finalización",
                icon = Icons.Default.Event,
                value = fechaFin,
                onValueChange = { fechaFin = it },
                isError = fechaFin.isNotBlank() && !isDateValid(fechaFin),
                errorMessage = "Formato de fecha inválido (dd/MM/yyyy)",
                placeholder = "dd/MM/yyyy"
            ),
            TournamentField(
                label = "Precio de Inscripción",
                icon = Icons.Default.AttachMoney,
                value = precio,
                onValueChange = { precio = it },
                keyboardType = KeyboardType.Number,
                isError = precio.isNotBlank() && !isPrecioValid,
                errorMessage = "Ingrese un precio válido",
                placeholder = "Ej: 1000.00"
            )
        )

        if (showDatePickerInicio) {
            showDatePicker { date ->
                fechaInicio = date
                showDatePickerInicio = false
            }
        }

        if (showDatePickerFin) {
            showDatePicker { date ->
                fechaFin = date
                showDatePickerFin = false
            }
        }

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
                        text = ADD_TORNEO,
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
                                trailingIcon = {
                                    if (field.label == "Fecha de Inicio") {
                                        IconButton(onClick = { showDatePickerInicio = true }) {
                                            Icon(
                                                imageVector = Icons.Default.CalendarToday,
                                                contentDescription = "Seleccionar Fecha de Inicio"
                                            )
                                        }
                                    }
                                    if (field.label == "Fecha de Finalización") {
                                        IconButton(onClick = { showDatePickerFin = true }) {
                                            Icon(
                                                imageVector = Icons.Default.CalendarToday,
                                                contentDescription = "Seleccionar Fecha de Finalización"
                                            )
                                        }
                                    }
                                }
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
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val torneo = Torneo(
                            nombre = nombre,
                            idTorneo = "1",
                            estado = "En Inscripción",
                            fechaFin = fechaFin,
                            fechaInicio = fechaInicio,
                            precio = precio,
                            ubicacion = ubicacion
                        )
                        addTorneo(torneo)
                        closeDialog()
                    },
                    enabled = fields.all { !it.isError } && fields.all { it.value.isNotBlank() }
                ) {
                    Text(ADD_TORNEO)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = closeDialog) {
                    Text(DISMISS)
                }
            }
        )

        LaunchedEffect(Unit) {
            coroutineContext.job.invokeOnCompletion {
                focusRequester.requestFocus()
            }
        }
    }
}

@Composable
fun showDatePicker(onDateSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            onDateSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}
