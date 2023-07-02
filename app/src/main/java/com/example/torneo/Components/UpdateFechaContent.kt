package com.example.torneo.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = fecha.numero.toString(),
            onValueChange = { numero->
                updateNumero(numero.toString())
            },
            placeholder = {
                Text("Numero de la Fecha")
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        fecha.estado?.let {
            TextField(
                value = it,
                onValueChange = { estado ->
                    updateEstado(estado)
                },
                placeholder = {
                    Text("Estado de la Fecha")
                }
            )
        }

        Button(
            onClick = {
                updateFecha(fecha)
                navigateBack()
            }
        ) {
            Text(text = "Actualizar")
        }
    }
}
