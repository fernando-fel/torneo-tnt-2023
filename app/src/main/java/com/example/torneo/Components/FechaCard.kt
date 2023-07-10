package com.example.torneo.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Fecha
import com.example.torneo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FechaCard(
    fecha: Fecha,
    deleteFecha: ()-> Unit,
    navigateToUpdateFechaScreen: (fechaId: Int)-> Unit,
    navigateToPartidoScreen: (fechaId: Int) -> Unit,
){
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation() ,
        onClick = {
            navigateToPartidoScreen(fecha.id)
        }

    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column() {
                Text("          FECHA       ")
                Text( "******* "+fecha.numero.toString()+" **********")
                Text("Estado de fecha: " + fecha.estado)
            }
            Spacer(
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {navigateToUpdateFechaScreen(fecha.id)}) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar Fecha" )
            }
            //Icon(imageVector = Icons.Default.AddTask, contentDescription = "Agregar Equipo" )

            IconButton(onClick = deleteFecha ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar Equipo" )
            }

        }
    }
}