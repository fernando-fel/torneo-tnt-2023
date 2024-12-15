package com.example.torneo.Components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.torneo.Core.Data.Entity.Partido
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePartidoContent(
    padding: PaddingValues,
    partido: Partido,
    updateDia: (dia: String) -> Unit,
    updateHora: (hora: String) -> Unit,
    navigateBack: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedFecha by remember { mutableStateOf(partido.dia) }
    var selectedHora by remember { mutableStateOf(partido.hora) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            label = { Text(text = "Dia de partido") },
            singleLine = true,
            value = partido.dia,
            onValueChange = { dia ->
                updateDia(dia)
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        Box {
            TextField(
                label = { Text(text = "Fecha de partido") },
                singleLine = true,
                value = selectedFecha,
                onValueChange = { },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.CalendarToday,
                        contentDescription = "Seleccionar fecha",
                        modifier = Modifier.clickable { showDatePicker = true }
                    )
                },
                readOnly = true,
                modifier = Modifier.clickable { showDatePicker = true }
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        Box {
            TextField(
                label = { Text(text = "Hora de partido") },
                singleLine = true,
                value = selectedHora,
                onValueChange = { },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Timer,
                        contentDescription = "Seleccionar hora",
                        modifier = Modifier.clickable { showTimePicker = true }
                    )
                },
                readOnly = true,
                modifier = Modifier.clickable { showTimePicker = true }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navigateBack() }) {
            Text(text = "Guardar cambios")
        }

        if (showDatePicker) {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                LocalContext.current,
                { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    selectedFecha = "$dayOfMonth/${month + 1}/$year"
                    updateDia(selectedFecha)
                    showDatePicker = false
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        if (showTimePicker) {
            val calendar = Calendar.getInstance()
            TimePickerDialog(
                LocalContext.current,
                { _: TimePicker, hour: Int, minute: Int ->
                    selectedHora = String.format("%02d:%02d", hour, minute)
                    updateHora(selectedHora)
                    showTimePicker = false
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }
    }
}
