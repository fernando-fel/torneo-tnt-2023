package com.example.torneo.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import com.example.torneo.Core.Constantes.Companion.ADD_TORNEO
import com.example.torneo.Core.Constantes.Companion.DISMISS
import com.example.torneo.Core.Constantes.Companion.NO_VALUE
import com.example.torneo.Core.Data.Entity.Torneo
import kotlinx.coroutines.job


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTorneosAlertDialog(
    openDialog: Boolean,
    closeDialog: ()->Unit,
    addTorneo: (torneo:Torneo) -> Unit
){

    if (openDialog){
        var nombre by remember { mutableStateOf(NO_VALUE) }
        var ubicacion by remember { mutableStateOf(NO_VALUE) }
        var fechaInicio by remember { mutableStateOf(NO_VALUE) }
        var fechaFin by remember { mutableStateOf(NO_VALUE) }
        var precio by remember { mutableStateOf(NO_VALUE) }
        var estado by remember { mutableStateOf(NO_VALUE) }

        val focusRequester = FocusRequester()

        AlertDialog(onDismissRequest = { closeDialog },
        title = {
            Text(ADD_TORNEO)
        },
            text = {
                Column{
                    TextField(
                        label = { Text(text = "Nombre del Torneo") },
                        singleLine = true,
                        value = nombre,
                        onValueChange = {nombre = it},
                        modifier = Modifier.focusRequester(
                            focusRequester
                        )
                    )
                    LaunchedEffect(Unit ){
                        coroutineContext.job.invokeOnCompletion {
                            focusRequester .requestFocus()
                        }
                    }
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        label = { Text(text = "Ubicación") },
                        singleLine = true,
                        value = ubicacion,
                        onValueChange =  {ubicacion = it},
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        label = { Text(text = "Fecha de Inicio") },
                        singleLine = true,
                        value = fechaInicio,
                        onValueChange =  {fechaInicio = it}
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        label = { Text(text = "Fecha de Finalización") },
                        singleLine = true,
                        value = fechaFin,
                        onValueChange =  {fechaFin = it}
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        label = { Text(text = "Precio de Inscripción") },
                        singleLine = true,
                        value = precio,
                        onValueChange =  {precio = it}
                    )
                    /*Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        label = { Text(text = "Estado") },
                        singleLine = true,
                        value = estado,
                        onValueChange =  {estado = it}
                    )*/

                    //(nombre, fecha inicio, fecha fin, ubicacion, precio, estado)
                }

            },
            confirmButton = {
                TextButton(
                    onClick = { closeDialog()
                        val torneo = Torneo(nombre = nombre, idTorneo = "1", estado = "En Inscripcion", fechaFin = fechaFin, fechaInicio = fechaInicio, precio = precio.toString(), ubicacion = ubicacion)
                        addTorneo(torneo)
                    },
                    enabled = !(nombre.isBlank() || ubicacion.isBlank() || fechaInicio.isBlank()
                            || fechaFin.isBlank() || precio.isBlank())
                ) {
                    Text(text = (ADD_TORNEO))
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