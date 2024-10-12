package com.example.torneo.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Torneo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTorneoContent(
    padding: PaddingValues,
    torneo: Torneo,
    updateNombre: (nombre:String) ->Unit,
    updateEstado: (estado: String) -> Unit,
    updateTorneo: (torneo:Torneo) -> Unit,
    navigateBack: () -> Unit
){
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(torneo.estado) }
    val options = listOf("En Curso", "Finalizados")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            label = { Text(text = "Nombre del Torneo") },
            singleLine = true,
            value = torneo.nombre,
            onValueChange = { nombre->
                updateNombre(nombre)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box {
            TextField(
                value = selectedOptionText,
                onValueChange = { estado->
                updateEstado(estado) },
                readOnly = true,
                label = { Text("Estado") },
                trailingIcon = {
                    Icon(
                        Icons.Filled.ArrowDropDown, "contentDescription",
                        Modifier.clickable { expanded = !expanded })
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { text ->
                    DropdownMenuItem(
                        text = { Text(text) },
                        onClick = {
                            selectedOptionText = text
                            updateEstado(text)
                            expanded = false
                        }
                    )
                }
            }
        }

        /*TextField(
            label = { Text(text = "Estado") },
            singleLine = true,
            value = torneo.estado,
            onValueChange = { nombre->
                updateEstado(nombre)
            }
        )*/

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                updateTorneo(torneo)
                navigateBack()
            }
        ) {
            Text(text = "Actualizar")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateEquipoContent(
    padding: PaddingValues,
    equipo: Equipo,
    updateNombre: (nombre:String) ->Unit,
    updateEquipo: (equipo:Equipo) -> Unit,
    navigateBack: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            label = { Text(text = "Nombre del Equipo") },
            singleLine = true,
            value = equipo.nombre,
            onValueChange = { nombre->
                updateNombre(nombre)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))


        Button(
            onClick = {
                updateEquipo(equipo)
                navigateBack()
            }
        ) {
            Text(text = "Actualizar")
        }
    }
}