package com.example.torneo.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import com.example.torneo.Core.Constantes.Companion.DISMISS
import com.example.torneo.Core.Constantes.Companion.NO_VALUE
import com.example.torneo.Core.Data.Entity.Fecha

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFechasAlertDialog(
    torneoId: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    addFecha: (fecha: Fecha) -> Unit
) {
    if (openDialog) {
        var numero by remember { mutableStateOf(NO_VALUE) }
        val focusRequester = remember { FocusRequester() }

        AlertDialog(
            onDismissRequest = { closeDialog() },
            title = {
                Text("Agregar Fecha")
            },
            text = {
                Column {
                    TextField(
                        label = { Text(text = "Número de fecha") },
                        singleLine = true,
                        value = numero,
                        onValueChange = { numero = it },
                        modifier = Modifier.focusRequester(focusRequester)
                    )
                    // Se puede usar LaunchedEffect para solicitar el enfoque si es necesario.
                    LaunchedEffect(openDialog) {
                        if (openDialog) {
                            focusRequester.requestFocus()
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (numero.isNotBlank()) {
                            val fecha = Fecha(
                                id = 0, // Deberías manejar el ID adecuadamente
                                idTorneo = torneoId.toInt().toString(),
                                numero = numero,
                                estado = "Empezado"
                            )
                            addFecha(fecha)
                            closeDialog()
                        }
                    },
                    enabled = numero.isNotBlank()
                ) {
                    Text(text = "Agregar Fecha")
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
