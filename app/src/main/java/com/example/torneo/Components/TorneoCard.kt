package com.example.torneo.Components


import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.torneo.Core.Data.Entity.Equipo
import com.example.torneo.Core.Data.Entity.Torneo
import com.example.torneo.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TorneoCard(
    torneo: Torneo,
    deleteTorneo: ()-> Unit,
    navigateToUpdateTorneoScreen: (torneoId: Int)-> Unit,
    navigateToFechasScreen: (torneoId: Int) -> Unit,
    navigateToInscripcionTorneoScreen: (torneoId: Int) -> Unit
){

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation() ,
            onClick = {
                if (torneo.estado== "En Inscripcion"){
                    navigateToInscripcionTorneoScreen(torneo.id)
                }
                else{
                    navigateToFechasScreen(torneo.id)
                }
        }

    ){
        val modifier = if (torneo.estado == "en curso") {
            Modifier
                .border(2.dp, Color.Green)
                .fillMaxWidth()
                .padding(12.dp)
        } else if(torneo.estado == "finalizados") {
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
            if (torneo.estado == "en curso") {
                Column() {
                    Text("NOMBRE DEL TORNEO: " + torneo.nombre,
                        fontWeight = FontWeight.Bold)
                    Text("Ubicacion: " + (torneo.ubicacion).toString())
                    Text("Fecha de Finalización: " + torneo.fechaFin)
                    Text("Estado: " + torneo.estado)
                }
            }
            else if(torneo.estado == "finalizados") {
                Column() {
                    Text("NOMBRE DEL TORNEO: " + torneo.nombre,
                        fontWeight = FontWeight.Bold)
                    Text("Ubicación: " + (torneo.ubicacion).toString())
                    Text("Fecha de Finalización: " + torneo.fechaFin)
                    Text("Estado: " + torneo.estado)
                }
            }
            else{
                Column() {
                    Text("NOMBRE DEL TORNEO: " + torneo.nombre,
                        fontWeight = FontWeight.Bold)
                    Text("Ubicación: " + (torneo.ubicacion).toString())
                    Text("Fecha de Inicio: "+torneo.fechaInicio)
                    Text("Fecha de Finalización: " + torneo.fechaFin)
                    Text("Precio del torneo: " + torneo.precio)
                    Text("Estado: " + torneo.estado)
                }
            }

            Spacer( modifier = Modifier.weight(1f) )
            IconButton(onClick = {navigateToUpdateTorneoScreen(torneo.id)}) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Modificar Torneo" )
            }
            IconButton(onClick = deleteTorneo ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar Torneo" )
            }
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
            .fillMaxWidth()
            .padding(8.dp, 4.dp, 8.dp, 4.dp),
        elevation = CardDefaults.cardElevation() ,
        onClick = {
            navigateToUpdateEquipoScreen(equipo.id)
        }
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Image(
                painterResource(R.drawable.balon_roto),
                contentDescription = "Imagen de prueba",
                modifier = Modifier.size(80.dp,60.dp)
            )
            Spacer( modifier = Modifier.width(10.dp) )
            Text(
                text = equipo.nombre,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.Serif
                )
            )

            Spacer( modifier = Modifier.weight(1f) )

            IconButton(onClick = {navigateToUpdateEquipoScreen(equipo.id)}) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Modificar Equipo" )
            }
            IconButton(onClick = deleteEquipo ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar Equipo" )
            }
        }
    }
}