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
import com.example.torneo.Core.Constantes.Companion.NOMBRE_TORNEO
import com.example.torneo.Core.Constantes.Companion.NO_VALUE
import com.example.torneo.Core.Data.Torneo
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
        var tipo by remember { mutableStateOf(NO_VALUE) }
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
                    value = tipo,
                    onValueChange =  {tipo = it},
                    placeholder = {Text("Tipo de torneo") }
                )}
            },
    confirmButton = {
        TextButton(
            onClick = { closeDialog()
            val torneo = Torneo(0,nombre,2023, tipo)
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