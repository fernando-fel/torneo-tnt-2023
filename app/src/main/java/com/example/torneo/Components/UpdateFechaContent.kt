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
import com.example.torneo.Core.Data.Entity.Fecha

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateFechasContent(
    padding: PaddingValues,
    fecha: Fecha,
    updateNumero: (numero:String) ->Unit,
    updateEstado: (estado: String) -> Unit,
    updateFecha: (fecha:Fecha) -> Unit,
    navigateBack: () -> Unit
){

    var expanded by remember { mutableStateOf(false) }
    // Inicializa el texto seleccionado con el estado actual del torneo
    var selectedOptionText by remember { mutableStateOf(fecha.estado) }
    val options = listOf("finalizado")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            label = { Text(text = "Numero de Fecha") },
            singleLine = true,
            value = fecha.numero.toString(),
            onValueChange = { numero->
                updateNumero(numero.toString())
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        /*fecha.estado?.let {
            TextField(
                label = { Text(text = "Estado") },
                singleLine = true,
                value = it,
                onValueChange = { estado ->
                    updateEstado(estado)
                }
            )
        }*/

        Box {
            TextField(
                value = selectedOptionText,
                onValueChange = { /* No se puede editar directamente */ },
                readOnly = true,
                label = { Text("Estado") },
                trailingIcon = {
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        contentDescription = "Menu",
                        Modifier.clickable { expanded = !expanded }
                    )
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
                            selectedOptionText = text // Actualiza el texto seleccionado
                            updateEstado(text) // Actualiza el estado en el ViewModel
                            expanded = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                updateFecha(fecha.copy(estado = selectedOptionText))
                navigateBack()
            }
        ) {
            Text(text = "Actualizar")
        }
    }
}
