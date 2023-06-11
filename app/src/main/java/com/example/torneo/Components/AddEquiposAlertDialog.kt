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
import com.example.torneo.Core.Data.Equipo
import com.example.torneo.Core.Data.Torneo
import kotlinx.coroutines.job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEquiposAlertDialog(
    openDialog: Boolean,
    closeDialog: ()->Unit,
    addEquipo: (equipo : Equipo) -> Unit
){
    if (openDialog){
        var nombre by remember { mutableStateOf(NO_VALUE) }
        val focusRequester = FocusRequester()

        AlertDialog(onDismissRequest = { closeDialog },
            title = {
                Text("Agregar Equipo")
            },
            text = {
                Column(){
                    TextField(value = nombre,
                        onValueChange = {nombre = it},
                        placeholder = {
                            Text("Nombre del Equipo")
                        },
                        modifier = Modifier.focusRequester(
                            focusRequester
                        )
                    )
                    LaunchedEffect(Unit ){
                        coroutineContext.job.invokeOnCompletion {
                            focusRequester .requestFocus()
                        }
                    } }
            },
            confirmButton = {
                TextButton(
                    onClick = { closeDialog()
                        val equipo = Equipo(0,nombre)
                        addEquipo(equipo)
                    }) {
                    Text(text = ("Agregar Equipo"))
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
