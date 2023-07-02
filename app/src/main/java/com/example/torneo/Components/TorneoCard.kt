package com.example.torneo.Components


import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Torneo
import com.example.torneo.Pantallas.Routes
import com.example.torneo.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TorneoCard(
    torneo: Torneo,
    deleteTorneo: ()-> Unit,
    navigateToUpdateTorneoScreen: (torneoId: Int)-> Unit,
    navigateToFechasScreen: (torneoId: Int) -> Unit
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
                navigateToFechasScreen(torneo.id)
        }

    ){
            val modifier = if (torneo.estado == "en Curso") {
                Modifier
                    .border(2.dp, Color.Green)
                    .fillMaxWidth()
                    .padding(12.dp)
            } else if(torneo.estado == "finalizado") {
                Modifier
                    .border(2.dp, Color.Red)
                    .fillMaxWidth()
                    .padding(12.dp)
            }
            else{
                Modifier
                    .border(2.dp, Color.White)
                    .fillMaxWidth()
                    .padding(12.dp)
            }

            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
            if (torneo.estado == "en Curso") {
                Column() {
                    Text("Nombre del torneo " + torneo.nombre)
                    Text("Ubicacion del torneo " + (torneo.ubicacion).toString())
                    Text("El torneo Finaliza el:  " + torneo.fechaFin)
                    Text("Estado del torneo: " + torneo.estado)
                }
            }
            else if(torneo.estado == "finalizado") {
                Column() {
                    Text("Nombre del torneo " + torneo.nombre)
                    Text("Ubicacion del torneo " + (torneo.ubicacion).toString())
                    Text("El torneo Finalizo el:  " + torneo.fechaFin)
                    Text("Estado del torneo: " + torneo.estado)
                }

            }
            else{
                Column() {
                Text("Nombre del torneo " + torneo.nombre)
                Text("Ubicacion del torneo " + (torneo.ubicacion).toString())
                Text("El torneo Finalizo el:  " + torneo.fechaFin)
                Text("Estado del torneo: " + torneo.estado)
                Text("Precio del torneo" + torneo.precio)
            }
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {navigateToUpdateTorneoScreen(torneo.id)}) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Agregar Torneo" )
            }
            DeleteIcon(
                deleteTorneo = deleteTorneo
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipoCard(
    equipo: Equipo,
    deleteEquipo: ()-> Unit,
    navigateToUpdateEquipoScreen: (equipoId: Int)-> Unit
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
            navigateToUpdateEquipoScreen(equipo.id)
        }

    ){
        Image(
            painterResource(R.drawable.download),
            contentDescription = "Imagen de prueba"
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column() {
                Text("Nombre")
                Text(text = equipo.nombre)
            }
            Spacer(
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {navigateToUpdateEquipoScreen(equipo.id)}) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Agregar Torneo" )
            }
            Icon(imageVector = Icons.Default.AddTask, contentDescription = "Agregar Equipo" )

            DeleteIcon2(
                deleteEquipo = deleteEquipo
            )
        }
    }
}