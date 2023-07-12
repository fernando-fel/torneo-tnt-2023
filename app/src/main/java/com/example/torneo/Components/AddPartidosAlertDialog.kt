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
    fechaId: Int,
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
        var golL by remember { mutableStateOf(0) }
        var golV by remember { mutableStateOf(0) }
        var estado by remember { mutableStateOf(NO_VALUE) }
        var juez by remember { mutableStateOf(NO_VALUE) }


        val focusRequester = FocusRequester()

        AlertDialog(onDismissRequest = { closeDialog },
            title = {
                Text("Agregar Partido")
            },
            text = {
                Column(){

                    TextField(
                        label = { Text(text = "Día del partido") },
                        singleLine = true,
                        value = dia,
                        onValueChange = {dia = it},
                        modifier = Modifier.focusRequester(
                            focusRequester
                        )
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        label = { Text(text = "Hora del partido") },
                        singleLine = true,
                        value = hora,
                        onValueChange =  {hora = it}
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        label = { Text(text = "Equipo Local") },
                        singleLine = true,
                        value = local,
                        onValueChange =  {local = it}
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        label = { Text(text = "Equipo Visitante") },
                        singleLine = true,
                        value = visitante,
                        onValueChange =  {visitante = it}
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        label = { Text(text = "Nombre de la Cancha") },
                        singleLine = true,
                        value = numeroCancha,
                        onValueChange =  {numeroCancha = it}
                    )
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                    TextField(
                        label = { Text(text = "Nombre del Juez") },
                        singleLine = true,
                        value = juez,
                        onValueChange =  {juez = it}
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
                        val partido = Partido(resultado = " -    -", numCancha = numeroCancha,
                                        idVisitante = visitante.toInt(), idLocal = local.toInt(),
                                        idFecha = fechaId, hora = hora, dia = dia,
                                        golVisitante = 0, golLocal = 0,
                                        estado = "Programado", id = 0, idPersona = juez)
                        addPartido(partido)
                    },
                    enabled = !(dia.isBlank() || hora.isBlank() || local.isBlank()
                            || visitante.isBlank() || numeroCancha.isBlank() || juez.isBlank())
                ) {
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

