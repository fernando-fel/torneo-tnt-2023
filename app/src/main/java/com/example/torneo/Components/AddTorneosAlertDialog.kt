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
                Column(){
                    TextField(value = nombre,
                        onValueChange = {nombre = it},
                        placeholder = {
                        Text("Nombre del torneo")
                    },
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
                    value = ubicacion,
                    onValueChange =  {ubicacion = it},
                    placeholder = {Text("Ubicacion del torneo") }
                )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = fechaInicio,
                        onValueChange =  {fechaInicio = it},
                        placeholder = {Text("Fecha de inicio del torneo") }
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = fechaFin,
                        onValueChange =  {fechaFin = it},
                        placeholder = {Text("Fecha de finalizacion del torneo") }
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = precio,
                        onValueChange =  {precio = it},
                        placeholder = {Text("Precio de inicio del torneo") }
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = estado,
                        onValueChange =  {estado = it},
                        placeholder = {Text("Estado del torneo") }
                    )

                    //(nombre, fecha inicio, fecha fin, ubicacion, precio, estado)
                }

            },
    confirmButton = {
        TextButton(
            onClick = { closeDialog()
            val torneo = Torneo(0, nombre = nombre, idTorneo = 1, estado = estado, fechaFin = fechaFin, fechaInicio = fechaInicio, precio = precio.toDouble(), ubicacion = ubicacion)
            addTorneo(torneo)
        }) {
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