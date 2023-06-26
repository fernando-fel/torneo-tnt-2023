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
import com.example.torneo.Core.Constantes.Companion.DISMISS
import com.example.torneo.Core.Constantes.Companion.NO_VALUE
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.Core.Data.Entity.Partido

import kotlinx.coroutines.job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPartidosAlertDialog(
    openDialog: Boolean,
    closeDialog: ()->Unit,
    addPartido: (partido: Partido) -> Unit
){
    if (openDialog){
        var fecha by remember { mutableStateOf(NO_VALUE) }
        var hora by remember { mutableStateOf(NO_VALUE) }
        var dia by remember { mutableStateOf(NO_VALUE) }
        var local by remember { mutableStateOf(NO_VALUE) }
        var visitante by remember { mutableStateOf(NO_VALUE) }
        var numeroCancha by remember { mutableStateOf(NO_VALUE) }
        var golL by remember { mutableStateOf(NO_VALUE) }
        var golV by remember { mutableStateOf(NO_VALUE) }
        var estado by remember { mutableStateOf(NO_VALUE) }


        val focusRequester = FocusRequester()

        AlertDialog(onDismissRequest = { closeDialog },
            title = {
                Text("Agregar Fecha")
            },
            text = {
                Column(){
                    TextField(value = fecha,
                        onValueChange = {fecha = it},
                        placeholder = {
                            Text("fecha")
                        },
                        modifier = Modifier.focusRequester(
                            focusRequester
                        )
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(value = dia,
                        onValueChange = {dia = it},
                        placeholder = {
                            Text("dia del partido")
                        },
                        modifier = Modifier.focusRequester(
                            focusRequester
                        )
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = hora,
                        onValueChange =  {hora = it},
                        placeholder = {Text("hora del Partido") }
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = local,
                        onValueChange =  {local = it},
                        placeholder = {Text("Equipo local") }
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = visitante,
                        onValueChange =  {visitante = it},
                        placeholder = {Text("Equipo Visitante") }
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = estado,
                        onValueChange =  {estado = it},
                        placeholder = {Text("Estado del partido") }
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        value = numeroCancha,
                        onValueChange =  {numeroCancha = it},
                        placeholder = {Text("Nombre de cancha") }
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
                        val partido = Partido(resultado = "", numCancha = numeroCancha,
                                        idVisitante = visitante.toInt(), idLocal = local.toInt(),
                                        idFecha = fecha.toInt(), hora = hora, dia = dia,
                                        golVisitante = 0, golLocal = 0,
                                        estado = estado, id = 0)
                        addPartido(partido)
                    }) {
                    Text(text = ("Agregar Partido"))
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

